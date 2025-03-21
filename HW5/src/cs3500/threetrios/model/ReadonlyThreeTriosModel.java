package cs3500.threetrios.model;

import java.util.List;

/**
 * Interface representing a read-only view of the ThreeTrios game model.
 * Provides methods to observe the current state of the game without allowing mutation.
 */
public interface ReadonlyThreeTriosModel {

  /**
   * Gets the size of the game grid.
   *
   * @return the size of the grid.
   */
  int gridSize();

  /**
   * Retrieves the content of a specific cell in the grid.
   *
   * @param row the row index of the cell (0-indexed).
   * @param col the column index of the cell (0-indexed).
   * @return the Card in the specified cell, or null if the cell is empty.
   */
  Card cellContents(int row, int col);

  /**
   * Gets the owner of a specific cell in the grid.
   *
   * @param row the row index of the cell (0-indexed).
   * @param col the column index of the cell (0-indexed).
   * @return the Player who owns the specified cell,
   *        or null if the cell has no owner.
   */
  GamePlayer getCellOwner(int row, int col);

  /**
   * Checks if a move to a specific cell is valid for the current player.
   *
   * @param row the row index of the cell (0-indexed).
   * @param col the column index of the cell (0-indexed).
   * @return true if the move to the specified cell is valid, {@code false} otherwise.
   */
  boolean isValidMove(int row, int col);

  /**
   * Calculates the maximum combo score that can be achieved by placing a card
   * at the specified location.
   *
   * @param card the {@link Card} to be placed.
   * @param row  the row index for placement (0-indexed).
   * @param col  the column index for placement (0-indexed).
   * @return the maximum combo score for placing the specified card at the given location.
   */
  int maxCombo(Card card, int row, int col);

  /**
   * Gets the score of the specified player.
   *
   * @param player the Player whose score is being retrieved.
   * @return the score of the specified player.
   */
  int getPlayerScore(GamePlayer player);

  /**
   * Checks if the game is over.
   *
   * @return {@code true} if the game is over, {@code false} otherwise.
   */
  boolean isGameOver();

  /**
   * Creates a copy of the current game grid.
   *
   * @return a {@link Grid} object representing a copy of the current game grid.
   */
  GameGrid getGrid();

  /**
   * Gets the current player's turn.
   *
   * @return the {@link Player} whose turn it is.
   */
  GamePlayer getCurrentPlayer();

  /**
   * Determines the winner of the game based on who owns more
   * card-cells on the grid and in their hand.
   *
   * @return the {@link Player} who has the most card-cells; null if there is no winner yet.
   */
  GamePlayer getWinner();

  /**
   * Gets a copy the list of players in the game.
   *
   * @return shallow copy of the list of players in the game.
   */
  List<GamePlayer> getPlayers();
}
