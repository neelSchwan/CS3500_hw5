package cs3500.threetrios.model;

import java.util.List;

public class GamePlayerImpl implements GamePlayer {
  private Player color;
  private List<Card> hand;

  GamePlayerImpl(Player color, List<Card> hand) {
    this.color = color;
    this.hand = hand;
  }

  /**
   * Gets the next card the player wants to play.
   *
   * @return the card the player has chosen to play.
   */
  @Override
  public Card chooseCard(int cardIndex) {
    return hand.get(cardIndex);
  }

  /**
   * Gets the next position (row, col) the player wants to place the chosen card.
   *
   * @return an array containing row and column, representing the position on the grid.
   */
  @Override
  public int[] choosePosition(int row, int col) {
    return new int[]{row, col};
  }

  /**
   * Gets the player's color.
   *
   * @return the player's color (RED or BLUE).
   */
  @Override
  public Player getColor() {
    return this.color;
  }

  /**
   * Gets the specified player's hand.
   *
   * @return COPY OF LIST OF CARDS in the specified player's hand.
   */
  @Override
  public List<Card> getPlayerHand() {
    return this.hand;
  }

  /**
   * Sets the color of the player (RED OR BLUE).
   *
   * @return
   */
  @Override
  public void setColor(Player color) {
    this.color = color;
  }
}
