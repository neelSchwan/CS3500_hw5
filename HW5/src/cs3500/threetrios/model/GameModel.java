package cs3500.threetrios.model;

import java.util.List;

/**
 * Interface defining a game model for the three-trios game.
 * Is an interface in case new models need to be implemented.
 */
public interface GameModel {

  /**
   * Given a row, column, and a card, place it in the grid, if the cell is a card-cell.
   *
   * @param row  specified row of a cell.
   * @param col  specified column of a cell.
   * @param card specified card object to put in the grid.
   */
  void placeCard(int row, int col, Card card);

  /**
   * Get the current player's turn.
   *
   * @return player whose turn it is.
   */
  Player getCurrentPlayer();

  /**
   * Checks if the game is over.
   *
   * @return true or false if the game is over or not.
   */
  boolean isGameOver();

  /**
   * Determines the winner of the game (who owns more of the card-cells in the grid).
   *
   * @return the player with the most card-cells.
   */
  Player getWinner();

  /**
   * Gets the state of the grid at the current moment.
   *
   * @return returns the current game grid.
   */
  Grid getGrid();

  /**
   * Gets the specified player's hand.
   *
   * @param player player (RED OR BLUE).
   * @return List of cards in the specified player's hand.
   */
  List<Card> getPlayerHand(Player player);

  /**
   * Method to switch to the next players turn
   * If current is RED, switch to blue, and vice versa.
   */
  void switchTurn();
}
