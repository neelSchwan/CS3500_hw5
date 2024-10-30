package cs3500.threetrios.model;

/**
 * Interface that represents a Player in the TreeTrios game.
 * This interface will be used in future extensions of the game so that human and AI players can
 * be implemented.
 */
public interface GamePlayer {
  /**
   * Gets the next card the player wants to play.
   *
   * @return the card the player has chosen to play.
   */
  Card chooseCard();

  /**
   * Gets the next position (row, col) the player wants to place the chosen card.
   *
   * @return an array containing row and column, representing the position on the grid.
   */
  int[] choosePosition();

  /**
   * Gets the player's color.
   *
   * @return the player's color (RED or BLUE).
   */
  Player getColor();
}

