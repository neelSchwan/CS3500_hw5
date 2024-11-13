package cs3500.threetrios.model;

import java.util.List;

public class AIPlayer implements GamePlayer {
  public AIPlayer(Player color, List<Card> hand, StrategyType strategy) {

  }

  /**
   * Gets the next card the player wants to play
   *
   * @param cardIndex index in the player's hand that they want to play.
   * @return the card the player has chosen to play.
   */
  @Override
  public Card chooseCard(int cardIndex) {
    return null;
  }

  /**
   * Gets the next position (row, col) the player wants to place the chosen card.
   *
   * @param row
   * @param col
   * @return an array containing row and column, representing the position on the grid.
   */
  @Override
  public int[] choosePosition(int row, int col) {
    return new int[0];
  }

  /**
   * Gets the player's color.
   *
   * @return the player's color (RED or BLUE).
   */
  @Override
  public Player getColor() {
    return null;
  }

  /**
   * Gets the specified player's hand.
   *
   * @return COPY OF LIST OF CARDS in the specified player's hand.
   */
  @Override
  public List<Card> getPlayerHand() {
    return List.of();
  }

  /**
   * Sets the color of the player (RED OR BLUE).
   *
   * @param color
   * @return
   */
  @Override
  public void setColor(Player color) {

  }

  @Override
  public void addCardToHand(Card card) {

  }

  @Override
  public void removeCardFromHand(Card card) {

  }
}
