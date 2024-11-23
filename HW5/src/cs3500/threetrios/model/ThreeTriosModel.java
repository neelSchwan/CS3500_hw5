package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 * Model for handling all game logic for the three-trios game.
 * This handles the battle phase, placing phase, and switching turns.
 */
public class ThreeTriosModel implements GameModel {
  private final Grid grid;
  private final List<GamePlayer> players;
  private GamePlayer currentPlayer;
  private final List<Card> deck;
  private boolean isGameStarted;
  private final List<GameModelListener> listeners;

  /**
   * Constructor for creating a ThreeTriosModel. This constructor DOES NOT start the game.
   * Instead, it creates, and initializes the grid, deck, and players.
   *
   * @param grid grid object to initialize.
   * @param deck List of cards that has all possible cards that could be in the game.
   *             Generally, this is decided by a config file.
   */
  public ThreeTriosModel(Grid grid, List<Card> deck) {
    if (grid == null || deck == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    this.grid = grid;
    this.deck = new ArrayList<>(deck);
    this.listeners = new ArrayList<>();
    this.isGameStarted = false;
    this.players = new ArrayList<>();
    grid.setupAdjacentCells();
  }

  /**
   * Adds a player to the final list of players, the first player will be the RED, and second BLUE.
   *
   * @param player player to add.
   */
  public void addPlayer(GamePlayer player) {
    if (players.size() >= 2) {
      throw new IllegalStateException("Only two players are allowed.");
    }
    if (players.isEmpty() && player.getColor() != Player.RED) {
      throw new IllegalArgumentException("The first player must be RED.");
    }

    if (players.size() == 1 && player.getColor() != Player.BLUE) {
      throw new IllegalArgumentException("The second player must be BLUE.");
    }
    players.add(player);
  }

  /**
   * Starts the game by shuffling the deck and dealing cards to the players.
   *
   * @param seed The random seed used to shuffle the deck for reproducibility.
   * @throws IllegalStateException if there are not enough cards in the deck to start the game.
   */
  @Override
  public void startGame(long seed) {
    validateGameStart();
    shuffleAndDealCards(seed);
    this.currentPlayer = players.get(0);
    this.isGameStarted = true;
    notifyTurnChanged();
  }

  private void validateGameStart() {
    if (players.size() != 2) {
      throw new IllegalStateException("The game requires exactly two players.");
    }

    int cardCells = grid.calculateCardCells();
    if (cardCells % 2 == 0) {
      throw new IllegalStateException("Grid must have an odd number of card cells.");
    }
    if (deck.size() < cardCells + 1) {
      throw new IllegalStateException("Not enough cards to start the game.");
    }
    if (deck.isEmpty()) {
      throw new IllegalStateException("Deck is empty");
    }
  }

  private void shuffleAndDealCards(long seed) {
    Collections.shuffle(deck, new Random(seed));
    int cardsPerPlayer = ((grid.calculateCardCells() + 1) / 2);
    dealCards(cardsPerPlayer);
  }

  /**
   * Deals the specified number of cards to each player from the deck.
   *
   * @param cardsPerPlayer The number of cards to deal to each player.
   */
  private void dealCards(int cardsPerPlayer) {
    for (int i = 0; i < cardsPerPlayer; i++) {
      players.get(0).addCardToHand(deck.remove(0));
      players.get(1).addCardToHand(deck.remove(0));
    }
  }

  private void validatePlacement(int row, int col, Card card) {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }
    Cell cell = grid.getCell(row, col);
    if (cell == null || cell.isHole() || cell.isOccupied()) {
      throw new IllegalArgumentException("Invalid cell for placing a card.");
    }
    if (!currentPlayer.getPlayerHand().contains(card)) {
      throw new IllegalArgumentException("Player does not have this card.");
    }
  }

  /**
   * Method to switch to the next players turn
   * If current is RED, switch to blue, and vice versa.
   */
  @Override
  public void switchTurn() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }

    if (currentPlayer == null) {
      throw new IllegalArgumentException("Current player cannot be null");
    }
    if (currentPlayer == players.get(0)) {
      currentPlayer = players.get(1);
    } else {
      currentPlayer = players.get(0);
    }
    notifyTurnChanged();
  }

  /**
   * Given a row, column, and a card, place it in the grid, if the cell is a card-cell.
   *
   * @param row  specified row of a cell.
   * @param col  specified column of a cell.
   * @param card specified card object to put in the grid.
   */
  @Override
  public void placeCard(int row, int col, Card card) {
    validatePlacement(row, col, card);

    Cell cell = grid.getCell(row, col);
    currentPlayer.removeCardFromHand(card);
    cell.placeCard(card, currentPlayer);
    battlePhase(cell);

    notifyGameStateUpdated();
    switchTurn();
  }

  private void battlePhase(Cell startingCell) {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }

    Set<Cell> visitedCells = new HashSet<>();

    List<Cell> flippedCells = battleAdjacentCells(startingCell);
    Queue<Cell> comboQueue = new LinkedList<>(flippedCells);

    while (!comboQueue.isEmpty()) {
      Cell currentCell = comboQueue.poll();
      if (visitedCells.contains(currentCell)) {
        continue;
      }
      visitedCells.add(currentCell);

      List<Cell> newFlippedCells = battleAdjacentCells(currentCell);
      comboQueue.addAll(newFlippedCells);
    }
  }

  private List<Cell> battleAdjacentCells(Cell cell) {
    List<Cell> flippedCells = new ArrayList<>();
    Map<Direction, Cell> adjacentCells = cell.getAdjacentCells();

    for (Map.Entry<Direction, Cell> entry : adjacentCells.entrySet()) {
      Direction direction = entry.getKey();
      Cell adjacentCell = entry.getValue();
      if (adjacentCell != null && adjacentCell.isOccupied()
              && adjacentCell.getOwner() != currentPlayer) {
        boolean flipped = battleCells(cell, adjacentCell, direction);
        if (flipped) {
          flippedCells.add(adjacentCell);
        }
      }
    }
    return flippedCells;
  }

  private boolean battleCells(Cell attackingCell, Cell defendingCell, Direction direction) {
    Card attackerCard = attackingCell.getCard();
    Card defenderCard = defendingCell.getCard();

    Direction oppositeDirection = getOppositeDirection(direction);

    int attackerAttackValue = attackerCard.getAttackValue(direction);
    int defenderAttackValue = defenderCard.getAttackValue(oppositeDirection);

    if (attackerAttackValue > defenderAttackValue) {
      GamePlayer attacker = attackingCell.getOwner();

      defendingCell.setOwner(attacker);
      return true; //flipped
    }
    return false; // not flipped
  }

  private Direction getOppositeDirection(Direction direction) {
    switch (direction) {
      case NORTH:
        return Direction.SOUTH;
      case SOUTH:
        return Direction.NORTH;
      case EAST:
        return Direction.WEST;
      case WEST:
        return Direction.EAST;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }
  // ---------------- READ ONLY MODEL METHODS -----------------------

  /**
   * Get the current player's turn.
   *
   * @return player whose turn it is.
   */
  @Override
  public GamePlayer getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Checks if the game is over. (The game is over when all the card cells are filled).
   *
   * @return true or false if the game is over or not.
   */
  @Override
  public boolean isGameOver() {
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell cell = grid.getCell(i, j);
        if (cell != null && !cell.isHole()) {
          if (!cell.isOccupied()) {
            return false;
          }
        }
      }
    }
    notifyGameOver();
    return true;
  }

  /**
   * Determines the winner of the game (which player has more cards total, in hand and on board).
   *
   * @return the player with the most card-cells.
   */
  @Override
  public GamePlayer getWinner() {
    int redScore = calculateScore(players.get(0));
    int blueScore = calculateScore(players.get(1));

    if (redScore > blueScore) {
      return players.get(0);
    }
    if (blueScore > redScore) {
      return players.get(1);
    }
    return null; // Tie
  }

  private int calculateScore(GamePlayer player) {
    int score = player.getPlayerHand().size();
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell cell = grid.getCell(i, j);
        if (cell != null && cell.isOccupied() && cell.getOwner() == player) {
          score++;
        }
      }
    }
    return score;
  }

  @Override
  public List<GamePlayer> getPlayers() {
    return new ArrayList<>(players);
  }

  /**
   * Creates a copy of the current game grid.
   *
   * @return a Grid object representing the current game grid.
   */
  @Override
  public Grid getGrid() {
    return grid;
  }

  /**
   * Gets the size of the game grid.
   *
   * @return the size of the grid.
   */
  @Override
  public int gridSize() {
    return grid.getRows() + grid.getCols(); //TODO: MIGHT CHANGE (IDK WHT THIS DOES)
  }

  /**
   * Retrieves the content of a specific cell in the grid.
   *
   * @param row the row index of the cell (0-indexed).
   * @param col the column index of the cell (0-indexed).
   * @return the Card in the specified cell, or null if the cell is empty.
   */
  @Override
  public Card cellContents(int row, int col) {
    if (!grid.getCell(row, col).isOccupied()) {
      throw new IllegalArgumentException("Cannot check content of unoccupied cell.");
    }
    return grid.getCell(row, col).getCard();
  }

  /**
   * Gets the owner of a specific cell in the grid.
   *
   * @param row the row index of the cell (0-indexed).
   * @param col the column index of the cell (0-indexed).
   * @return the GamePlayer who owns the specified cell.
   */
  @Override
  public GamePlayer getCellOwner(int row, int col) {
    return grid.getCell(row, col).getOwner();
  }

  /**
   * Checks if a move to a specific cell is valid for the current player.
   *
   * @param row the row index of the cell (0-indexed).
   * @param col the column index of the cell (0-indexed).
   * @return true if the move to the specified cell is valid, false otherwise.
   */
  @Override
  public boolean isValidMove(int row, int col) {
    if (row < 0 || row >= grid.getRows() || col < 0 || col >= grid.getCols()) {
      return false;
    }

    Cell cell = grid.getCell(row, col);

    return cell != null && !cell.isOccupied() && !cell.isHole();
  }

  /**
   * Calculates the max number cards a player can flip by placing a card at the specified location.
   *
   * @param card the Card to be placed.
   * @param row  the row index for placement (0-indexed).
   * @param col  the column index for placement (0-indexed).
   * @return max number cards a player can flip by placing the specified card at the given location.
   */
  @Override
  public int maxCombo(Card card, int row, int col) {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }

    Cell targetCell = grid.getCell(row, col);
    if (targetCell == null || targetCell.isHole() || targetCell.isOccupied()) {
      throw new IllegalArgumentException("Invalid cell for placing a card.");
    }
    // Initialize the simulated game state
    Map<Cell, GamePlayer> simulatedOwners = new HashMap<>();
    Map<Cell, Card> simulatedCards = new HashMap<>();

    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell cell = grid.getCell(i, j);
        if (cell != null && cell.isOccupied()) {
          simulatedOwners.put(cell, cell.getOwner());
          simulatedCards.put(cell, cell.getCard());
        }
      }
    }

    simulatedOwners.put(targetCell, currentPlayer);
    simulatedCards.put(targetCell, card);

    return simulateBattlePhase(targetCell, simulatedOwners, simulatedCards);
  }

  // for each cell check adjacent and simualte battle
  private int simulateBattlePhase(Cell startingCell, Map<Cell, GamePlayer> simulatedOwners,
                                  Map<Cell, Card> simulatedCards) {
    int flipCount = 0;
    Set<Cell> visitedCells = new HashSet<>();
    Queue<Cell> comboQueue = new LinkedList<>();
    comboQueue.add(startingCell);

    while (!comboQueue.isEmpty()) {
      Cell currentCell = comboQueue.poll();
      if (visitedCells.contains(currentCell)) {
        continue;
      }
      visitedCells.add(currentCell);

      Card currentCard = simulatedCards.get(currentCell);
      GamePlayer currentOwner = simulatedOwners.get(currentCell);

      List<Cell> flippedCells = simulateBattleAdjacentCells(currentCell, currentCard, currentOwner,
              simulatedOwners, simulatedCards);

      flipCount += flippedCells.size();

      comboQueue.addAll(flippedCells);
    }

    return flipCount;
  }

  private List<Cell> simulateBattleAdjacentCells(Cell cell, Card card, GamePlayer owner,
                                                 Map<Cell, GamePlayer> simulatedOwners,
                                                 Map<Cell, Card> simulatedCards) {
    List<Cell> flippedCells = new ArrayList<>();
    Map<Direction, Cell> adjacentCells = cell.getAdjacentCells();

    for (Map.Entry<Direction, Cell> entry : adjacentCells.entrySet()) {
      Direction direction = entry.getKey();
      Cell adjacentCell = entry.getValue();
      if (adjacentCell != null && simulatedCards.containsKey(adjacentCell)) {
        GamePlayer adjacentOwner = simulatedOwners.get(adjacentCell);
        if (adjacentOwner != owner) {
          boolean flipped = simulateBattleBetweenCells(cell, card, adjacentCell, direction,
                  simulatedOwners, simulatedCards);
          if (flipped) {
            flippedCells.add(adjacentCell);
          }
        }
      }
    }
    return flippedCells;
  }

  private boolean simulateBattleBetweenCells(Cell attackingCell, Card attackingCard,
                                             Cell defendingCell, Direction direction,
                                             Map<Cell, GamePlayer> simulatedOwners,
                                             Map<Cell, Card> simulatedCards) {
    Card defenderCard = simulatedCards.get(defendingCell);

    Direction oppositeDirection = getOppositeDirection(direction);

    int attackerAttackValue = attackingCard.getAttackValue(direction);
    int defenderAttackValue = defenderCard.getAttackValue(oppositeDirection);

    if (attackerAttackValue > defenderAttackValue) {
      // Flip the defender's cell in the simulation
      simulatedOwners.put(defendingCell, simulatedOwners.get(attackingCell));
      return true; // Flipped
    }
    return false; // Not flipped
  }

  /**
   * Gets the score of the specified player. (Num cells they own + num cards in hand).
   *
   * @param player the Player whose score is being retrieved.
   * @return the score of the specified player.
   */
  @Override
  public int getPlayerScore(GamePlayer player) {
    int playerScore = 0;
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell cell = grid.getCell(i, j);
        if (cell.getOwner() == player) {
          playerScore++;
          System.out.println(playerScore);
        }
      }
    }
    System.out.println(playerScore + player.getPlayerHand().size());
    return playerScore + player.getPlayerHand().size();
  }

  // ------------ MODEL EVENT LISTENER METHODS ----------------

  /**
   * Adds a listener for model events.
   *
   * @param listener the listener to add
   */
  public void addGameModelListener(GameModelListener listener) {
    listeners.add(listener);
  }

  /**
   * Notifies listeners of a turn change.
   */
  private void notifyTurnChanged() {
    for (GameModelListener listener : listeners) {
      listener.onTurnChanged(currentPlayer);
    }
  }

  /**
   * Notifies listeners of a game state update.
   */
  private void notifyGameStateUpdated() {
    for (GameModelListener listener : listeners) {
      listener.onGameStateUpdated();
    }
  }

  /**
   * Notifies listeners of the end of the game.
   */
  private void notifyGameOver() {
    GamePlayer winner = getWinner();
    for (GameModelListener listener : listeners) {
      listener.onGameOver(winner);
    }
  }
}
