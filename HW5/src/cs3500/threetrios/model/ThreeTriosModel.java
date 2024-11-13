package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.Collections;
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


  /**
   * @param grid
   * @param playerFactory
   * @param deck
   */
  public ThreeTriosModel(Grid grid, PlayerFactory playerFactory, List<Card> deck) {
    if (grid == null || playerFactory == null || deck == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    this.grid = grid;
    this.deck = deck;
    this.players = new ArrayList<>();
    this.isGameStarted = false;
    this.grid.setupAdjacentCells();

    players.add(playerFactory.createPlayer(Player.RED, new ArrayList<>())); // 0 RED
    players.add(playerFactory.createPlayer(Player.BLUE, new ArrayList<>())); // 1 BLUE
  }

  /**
   * Starts the game by shuffling the deck and dealing cards to the players.
   *
   * @param seed The random seed used to shuffle the deck for reproducibility.
   * @throws IllegalStateException if there are not enough cards in the deck to start the game.
   */
  @Override
  public void startGame(long seed) {
    int cardCells = grid.calculateCardCells(); // N
    //INVARIANT: the number of card cells is always odd. This cannot change because one the game
    //           is started the number of card cells cannot change.
    if (cardCells % 2 == 0) {
      throw new IllegalStateException("Grid must have an odd number of card cells.");
    }

    if (deck.size() < cardCells + 1) { //deck size < N
      throw new IllegalStateException("Not enough cards to start the game.");
    }

    Collections.shuffle(deck, new Random(seed));

    int cardsPerPlayer = ((cardCells + 1) / 2); // N+1/2 CARDS PER PLAYER.
    dealCards(cardsPerPlayer);

    this.currentPlayer = players.get(0);
    this.isGameStarted = true;
  }

  /**
   * Initializes the game grid and player hands based on a configuration file.
   *
   * @param gridFile the file containing the grid configuration.
   * @param cardFile the file containing the card configuration.
   * @throws IllegalArgumentException if the configuration files are invalid or do not match expected formats.
   */
  @Override
  public void initializeGame(String gridFile, String cardFile) {

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

  /**
   * Given a row, column, and a card, place it in the grid, if the cell is a card-cell.
   *
   * @param row  specified row of a cell.
   * @param col  specified column of a cell.
   * @param card specified card object to put in the grid.
   */
  @Override
  public void placeCard(int row, int col, Card card) {
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
    if (currentPlayer.getPlayerHand().isEmpty()) {
      throw new IllegalArgumentException("Player has no cards to place.");
    }

    currentPlayer.removeCardFromHand(card);

    cell.placeCard(card, currentPlayer);

    battlePhase(cell);

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
      GamePlayer defender = defendingCell.getOwner();

      defender.removeCardFromHand(defenderCard);
      attacker.removeCardFromHand(attackerCard);

      defendingCell.setOwner(attackingCell.getOwner());
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
            System.out.println(" i returned false, game isn't over");
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Determines the winner of the game (which player has more cards total, in hand and on board).
   *
   * @return the player with the most card-cells.
   */
  @Override
  public GamePlayer getWinner() {
    int redCount = 0;
    int blueCount = 0;

    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell currentCell = grid.getCell(i, j);
        if (currentCell.isOccupied()) {
          GamePlayer cardOwner = currentCell.getOwner();
          if (cardOwner.getColor() == Player.RED) {
            redCount++;
          } else if (cardOwner.getColor() == Player.BLUE) {
            blueCount++;
          }
        }
      }
    }

    redCount += players.get(0).getPlayerHand().size(); //0 = red
    blueCount += players.get(1).getPlayerHand().size(); //1 = blue

    if (redCount > blueCount) {
      return players.get(0);
    } else if (blueCount > redCount) {
      return players.get(1);
    } else {
      return null; // Tie
    }
  }

  @Override
  public List<GamePlayer> getPlayers() {
    return players;
  }

  /**
   * Creates a copy of the current game grid.
   *
   * @return a Grid object representing a copy of the current game grid.
   */
  @Override
  public Grid getGrid() { //TODO: TEST THIS
    Grid gridCopy = new Grid(grid.getRows(), grid.getCols());
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell cell = grid.getCell(i, j);
        gridCopy.setCell(i, j, cell);
      }
    }
    return gridCopy;
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
   * @return the {@link Player} who owns the specified cell, or {@code null} if the cell has no owner.
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
    return simulateFlips(card, row, col);
  }

  private int simulateFlips(Card card, int row, int col) {
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

    Set<Cell> visitedCells = new HashSet<>();
    List<Cell> flippedCells = battleAdjacentCells(cell);
    Queue<Cell> comboQueue = new LinkedList<>(flippedCells);

    int flipCount = flippedCells.size();
    while (!comboQueue.isEmpty()) {
      Cell currentCell = comboQueue.poll();
      if (visitedCells.contains(currentCell)) {
        continue;
      }
      visitedCells.add(currentCell);

      List<Cell> newFlippedCells = battleAdjacentCells(currentCell);
      comboQueue.addAll(newFlippedCells);
      flipCount += newFlippedCells.size();
    }

    return flipCount;
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
  }
}
