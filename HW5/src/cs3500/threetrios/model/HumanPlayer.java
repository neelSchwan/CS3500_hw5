package cs3500.threetrios.model;

import java.util.List;

/**
 * HumanPlayer class that represents a human who's playing the three-trios game.
 * This player has a color and hand associated with it, and is used when declaring players
 */
public class HumanPlayer implements GamePlayer {
  private Player color;
  private final List<Card> hand;

  /**
   * Constructor for creating a human player.
   * This player allows for humans to interact with the GUI when passed into the controller.
   *
   * @param color which player this color is, (RED OR BLUE).
   * @param hand  the initial hand this player starts with.
   */
  public HumanPlayer(Player color, List<Card> hand) {
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
   */
  @Override
  public void setColor(Player color) {
    this.color = color;
  }

  @Override
  public void addCardToHand(Card card) {
    hand.add(card);
  }

  @Override
  public void removeCardFromHand(Card card) {
    hand.remove(card);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    HumanPlayer other = (HumanPlayer) obj;
    return this.color == other.color;
  }

  @Override
  public int hashCode() {
    return this.color.hashCode();
  }
}
