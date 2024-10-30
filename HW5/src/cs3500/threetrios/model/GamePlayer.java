package cs3500.threetrios.model;

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
   * Updates the player's hand after they have placed a card.
   *
   * @param card the card that was placed.
   */
  void updateHand(Card card);

  /**
   * Gets the player's color.
   *
   * @return the player's color (RED or BLUE).
   */
  Player getColor();
}

