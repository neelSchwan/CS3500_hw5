package cs3500.threetrios.adapter;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.view.ThreeTriosView;
import cs3500.threetrios.provider.view.ViewListener;

public class ProviderViewAdapter implements ThreeTriosView {
  private final ReadonlyThreeTriosModel model;
  private final ThreeTriosView providerView;

  public ProviderViewAdapter(ReadonlyThreeTriosModel model, ThreeTriosView providerView) {
    this.model = model;
    this.providerView = providerView;
  }

  /**
   * Makes the view visible.
   */
  @Override
  public void makeVisible() {
    providerView.makeVisible();
  }

  /**
   * Refreshes the view to reflect any changes in the model.
   */
  @Override
  public void refresh() {
    providerView.refresh();
  }

  /**
   * Highlights the selected card in the view.
   *
   * @param cardIndex index of the card selected
   * @param player    player color
   */
  @Override
  public void highlightSelectedCard(int cardIndex, Player player) {

  }

  /**
   * Handles showing an error whenever the view encounters an error.
   *
   * @param message error message
   */
  @Override
  public void showError(String message) {

  }

  /**
   * Updates the status of the game which will be shown in the window panel.
   *
   * @param status game status
   */
  @Override
  public void updateStatus(String status) {

  }

  /**
   * Enables the input of a view.  This makes sure the player cannot make
   * a move while it is the other players turn.
   *
   * @param enabled enabled or not
   */
  @Override
  public void setInputEnabled(boolean enabled) {

  }

  /**
   * Displays a dialog box to inform the player that the game is over, showing the winner and the
   * score.
   *
   * @param message The message to display to the user.
   */
  @Override
  public void showGameOver(String message) {

  }

  /**
   * Used to bring the current players window to the front.
   */
  @Override
  public void bringToFront() {

  }

  /**
   * Adds a view listener.
   *
   * @param listener listener
   */
  @Override
  public void addViewListener(ViewListener listener) {

  }

  /**
   * Removes a view listener.
   *
   * @param listener listener
   */
  @Override
  public void removeViewListener(ViewListener listener) {

  }
}
