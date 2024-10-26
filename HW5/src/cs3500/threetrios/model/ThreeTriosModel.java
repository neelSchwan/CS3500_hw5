package cs3500.threetrios.model;

import java.util.List;
import java.util.Map;

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
    this.currentPlayer = Player.RED;
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

    if (cell.isOccupied()) {
      throw new IllegalArgumentException("This cell is already occupied.");
    }

    cell.placeCard(card, currentPlayer);
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
        if (!grid.getCell(i, j).isOccupied()) {
          return false;
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
