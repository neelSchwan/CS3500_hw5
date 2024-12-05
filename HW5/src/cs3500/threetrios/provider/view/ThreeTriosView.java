package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.Player;

/**
 * The interface for the ThreeTriosView.
 */
public interface ThreeTriosView extends ViewFeatures {
  /**
   * Makes the view visible.
   */
  void makeVisible();

  /**
   * Refreshes the view to reflect any changes in the model.
   */
  void refresh();

  /**
   * Highlights the selected card in the view.
   *
   * @param cardIndex index of the card selected
   * @param player    player color
   */
  void highlightSelectedCard(int cardIndex, Player player);

  /**
   * Handles showing an error whenever the view encounters an error.
   *
   * @param message error message
   */
  void showError(String message);

  /**
   * Updates the status of the game which will be shown in the window panel.
   *
   * @param status game status
   */
  void updateStatus(String status);

  /**
   * Enables the input of a view.  This makes sure the player cannot make
   * a move while it is the other players turn.
   *
   * @param enabled enabled or not
   */
  void setInputEnabled(boolean enabled);

  /**
   * Displays a dialog box to inform the player that the game is over, showing the winner and the
   * score.
   *
   * @param message The message to display to the user.
   */
  void showGameOver(String message);

  /**
   * Used to bring the current players window to the front.
   */
  void bringToFront();
}

