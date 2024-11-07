package cs3500.threetrios.model;

import cs3500.threetrios.view.ReadonlyThreeTriosModel;

/**
 * Interface defining a mutable game model for the ThreeTrios game.
 * Provides methods to modify the game state, such as placing cards, switching turns, and starting the game.
 */
public interface GameModel extends ReadonlyThreeTriosModel {

  /**
   * Places a card in the grid at the specified row and column if the cell is a card-cell.
   *
   * @param row  the row index of the cell (0-indexed).
   * @param col  the column index of the cell (0-indexed).
   * @param card the {@link Card} object to place in the grid.
   */
  void placeCard(int row, int col, Card card);

  /**
   * Switches to the next player's turn. If the current player is RED, switches to BLUE, and vice versa.
   */
  void switchTurn();

  /**
   * Starts the game by shuffling the deck and dealing cards to the players.
   *
   * @param seed the random seed used to shuffle the deck for reproducibility.
   * @throws IllegalStateException if there are not enough cards in the deck to start the game.
   */
  void startGame(long seed);

  /**
   * Initializes the game grid and player hands based on a configuration file.
   *
   * @param gridFile the file containing the grid configuration.
   * @param cardFile the file containing the card configuration.
   * @throws IllegalArgumentException if the configuration files are invalid or do not match expected formats.
   */
  void initializeGame(String gridFile, String cardFile);
}
