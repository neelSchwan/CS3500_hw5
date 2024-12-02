package cs3500.view;

/**
 * The listener for the hand panel.  This allows the hand panel
 * to know when a card is selected.
 */
public interface HandPanelListener {
  /**
   * The listener gets notified when a card is selected.
   *
   * @param cardIndex index of the card selected
   */
  void cardSelected(int cardIndex);
}
