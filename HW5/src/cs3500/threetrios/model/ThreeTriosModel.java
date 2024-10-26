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

public class ThreeTriosModel implements GameModel {

  private final Grid grid;
  private final Map<Player, List<Card>> hands;
  private Player currentPlayer;
  private final List<Card> deck;

  //TODO: ADD CHECKS FOR DRAWING FROM DECK TO PLAYERS HANDS.
  //TODO: REMOVE FROM DECK WHEN DRAW, REMOVE FROM HAND WHEN PLACING

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

    if (deck.size() < cardCells + 1) { //deck size < N
      throw new IllegalStateException("Not enough cards to start the game.");
    }

    Collections.shuffle(deck, new Random(seed));

    int cardsPerPlayer = ((cardCells + 1) / 2); // N+1/2 CARDS PER PLAYER.

    dealCards(cardsPerPlayer);

    this.currentPlayer = Player.RED;
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
    Cell cell = grid.getCell(row, col);
    if (cell == null || cell.isHole() || cell.isOccupied()) {
      throw new IllegalArgumentException("Invalid cell for placing a card.");
    }
    if (!getPlayerHand(currentPlayer).contains(card)) {
      throw new IllegalArgumentException("Player does not have this card.");
    }

    getPlayerHand(currentPlayer).remove(card);
    cell.placeCard(card, currentPlayer);

    battlePhase(cell);

    switchTurn();
  }

  private void battlePhase(Cell startingCell) {
    Queue<Cell> comboQueue = new LinkedList<>();
    Set<Cell> visitedCells = new HashSet<>();

    List<Cell> flippedCells = battleAdjacentCells(startingCell);
    comboQueue.addAll(flippedCells);

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

    Position cellPosition = grid.findCellPosition(cell);
    if (cellPosition == null) {
      throw new IllegalArgumentException("Cell position not found in the grid.");
    }
    Map<Direction, Cell> adjacentCells = grid.getAdjacentCells(cellPosition.row, cellPosition.col);

    for (Map.Entry<Direction, Cell> entry : adjacentCells.entrySet()) {
      Direction direction = entry.getKey();
      Cell adjacentCell = entry.getValue();

      if (adjacentCell != null && adjacentCell.isOccupied() && adjacentCell.getOwner() != currentPlayer) {
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
      defendingCell.setOwner(attackingCell.getOwner());
      return true;
    }
    return false;
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
   * Checks if the game is over.
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
    return true;
  }

  /**
   * Determines the winner of the game (who owns more of the card-cells in the grid).
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

          if (cardOwner == Player.BLUE) {
            blueCount++;
          } else if (cardOwner == Player.RED) {
            redCount++;

          }
        }
      }
    }

    if (redCount > blueCount) {
      return Player.RED;
    } else if (blueCount > redCount) {
      return Player.BLUE;
    } else {
      return null;
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
   * @return List of cards in the specified player's hand.
   */
  @Override
  public List<Card> getPlayerHand(Player player) {
    return hands.get(player);
  }

  /**
   * Method to switch to the next players turn
   * If current is RED, switch to blue, and vice versa.
   */
  @Override
  public void switchTurn() {
    if (currentPlayer == null) {
      throw new IllegalArgumentException("Current player cannot be null");
    }

    currentPlayer = (currentPlayer == Player.RED) ? Player.BLUE : Player.RED;
  }


}
