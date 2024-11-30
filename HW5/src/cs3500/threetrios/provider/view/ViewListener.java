package cs3500.threetrios.provider.view;

/**
 * ViewListener to receive events from the view.
 */
public interface ViewListener {

  /**
   * When a player selects a card from their hand
   * the listener will receive this event.
   *
   * @param cardIndex index of card
   */
  void onCardSelected(int cardIndex);

  /**
   * When a player selects a position on the board
   * to place a card, the listener will receive this
   * event.
   *
   * @param row row of position
   * @param col col of position
   */
  void onPosSelected(int row, int col);
}
