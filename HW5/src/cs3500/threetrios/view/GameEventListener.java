package cs3500.threetrios.view;

import cs3500.threetrios.model.GamePlayer;

/**
 * Listener interface to handle events triggered in the view such as mouse clicks.
 * Enables the controller to respond to user actions like clicking on a card in the hand,
 * or on the grid.
 */
public interface GameEventListener {
  /**
   * Triggered when a card in a player's hand is selected.
   *
   * @param cardIndex the index of the selected card within the player's hand.
   * @param player    the player whose card is selected.
   *                  This ensures that there can't be two cards highlighted.
   */
  void onCardSelected(int cardIndex, GamePlayer player);

  /**
   * Triggered when a cell on the game grid is clicked.
   *
   * @param row the row index of the clicked cell
   * @param col the column index of the clicked cell
   */
  void onCellClicked(int row, int col);
}
