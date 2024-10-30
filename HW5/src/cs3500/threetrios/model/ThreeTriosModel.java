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
  private final Map<Player, List<Card>> hands;
  private Player currentPlayer;
  private final List<Card> deck;
  private boolean isGameStarted;

  /**
   * Constructor to create a ThreeTriosModel object.
   *
   * @param grid  takes in a grid that is used in the game.
   * @param hands takes in the hand of each player as a map, where the key is the player
   *              ,and the value is a list of cards in that players hand.
   * @param deck  List of cards that is populated with cards from a config file.
   */
  public ThreeTriosModel(Grid grid, Map<Player, List<Card>> hands, List<Card> deck) {
    if (grid == null || hands == null || deck == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }

    this.grid = grid;
    this.hands = hands;
    this.deck = deck;
    this.isGameStarted = false;
    this.grid.setupAdjacentCells();
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

    if (cardCells % 2 == 0) {
      throw new IllegalStateException("Grid must have an odd number of card cells.");
    }

    if (deck.size() < cardCells + 1) { //deck size < N
      throw new IllegalStateException("Not enough cards to start the game.");
    }

    Collections.shuffle(deck, new Random(seed));

    int cardsPerPlayer = ((cardCells + 1) / 2); // N+1/2 CARDS PER PLAYER.

    dealCards(cardsPerPlayer);

    this.currentPlayer = Player.RED;
    this.isGameStarted = true;
  }

  /**
   * Deals the specified number of cards to each player from the deck.
   *
   * @param cardsPerPlayer The number of cards to deal to each player.
   */
  private void dealCards(int cardsPerPlayer) {
    List<Card> redPlayerHand = new ArrayList<>();
    List<Card> bluePlayerHand = new ArrayList<>();

    for (int i = 0; i < cardsPerPlayer; i++) {
      redPlayerHand.add(deck.remove(0));
      bluePlayerHand.add(deck.remove(0));
    }

    hands.put(Player.RED, redPlayerHand);
    hands.put(Player.BLUE, bluePlayerHand);

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
    if (!(hands.get(currentPlayer).contains(card))) {
      throw new IllegalArgumentException("Player does not have this card.");
    }
    if (hands.get(currentPlayer).isEmpty()) {
      throw new IllegalArgumentException("Player has no cards to place.");
    }
    hands.get(currentPlayer).remove(card);

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
      Player attacker = attackingCell.getOwner();
      Player defender = defendingCell.getOwner();

      hands.get(defender).remove(defenderCard);
      hands.get(attacker).remove(attackerCard);

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
  public Player getCurrentPlayer() {
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
            System.out.println(" i returned false, game isnt over");
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
  public Player getWinner() {
    int redCount = 0;
    int blueCount = 0;

    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell currentCell = grid.getCell(i, j);
        if (currentCell.isOccupied()) {
          Player cardOwner = currentCell.getOwner();
          if (cardOwner == Player.RED) {
            redCount++;
          } else if (cardOwner == Player.BLUE) {
            blueCount++;
          }
        }
      }
    }

    redCount += hands.get(Player.RED).size();
    blueCount += hands.get(Player.BLUE).size();

    if (redCount > blueCount) {
      return Player.RED;
    } else if (blueCount > redCount) {
      return Player.BLUE;
    } else {
      return null; // Tie
    }
  }

  /**
   * Gets the state of the grid at the current moment.
   *
   * @return returns the current game grid.
   */
  @Override
  public Grid getGrid() {
    return grid;
  }

  /**
   * Gets the specified player's hand.
   *
   * @param player player (RED OR BLUE).
   * @return COPY OF LIST OF CARDS in the specified player's hand.
   */
  @Override
  public List<Card> getPlayerHand(Player player) {
    return new ArrayList<>(hands.get(player));
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

    currentPlayer = (currentPlayer == Player.RED) ? Player.BLUE : Player.RED;
  }
}
