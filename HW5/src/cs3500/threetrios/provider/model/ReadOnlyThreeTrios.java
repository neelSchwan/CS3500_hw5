package cs3500.threetrios.provider.model;

import java.util.List;

/**
 * Read-only interface for the ThreeTrios game model.
 * Provides methods to observe the state of the game without modifying it.
 */
public interface ReadOnlyThreeTrios {

  /**
   * Gets the current grid of the game as a 2d array.
   *
   * @return a 2D array representing the current grid state
   */
  Cell[][] getCurrentGrid();

  /**
   * Gets the current player's turn.
   *
   * @return the player whose turn it is
   */
  Player getTurn();

  /**
   * Gets a copy of the specified player's hand.
   *
   * @param player the player whose hand is requested
   * @return a list of cards in the player's hand
   */
  List<Card> getHand(Player player);

  /**
   * Determines the winner of the game if the game is over.
   *
   * @return the winning player, or null if it's a tie
   * @throws IllegalStateException if the game is not over yet
   */
  Player getWinner();
}
