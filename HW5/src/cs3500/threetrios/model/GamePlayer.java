package cs3500.threetrios.model;

import java.util.List;

/**
 * Interface that represents a Player in the TreeTrios game.
 * This interface will be used in future extensions of the game so that human and AI players can
 * be implemented.
 */
public interface GamePlayer {

  /**
   * Gets the next card the player wants to play
   *
   * @param cardIndex index in the player's hand that they want to play.
   * @return the card the player has chosen to play.
   */
  Card chooseCard(int cardIndex);

  /**
   * Gets the next position (row, col) the player wants to place the chosen card.
   *
   * @return an array containing row and column, representing the position on the grid.
   */
  int[] choosePosition(int row, int col);

  /**
   * Gets the player's color.
   *
   * @return the player's color (RED or BLUE).
   */
  Player getColor();

  /**
   * Gets the specified player's hand.
   *
   * @return COPY OF LIST OF CARDS in the specified player's hand.
   */
  List<Card> getPlayerHand();

  /**
   * Sets the color of the player (RED OR BLUE).
   */
  void setColor(Player color);

  /**
   * Adds a card to the specified players hand.
   *
   * @param card card to add to the hand.
   */
  void addCardToHand(Card card);

  /**
   * removes a card from the players hand list.
   *
   * @param card card object to remove.
   */
  void removeCardFromHand(Card card);

}

