package cs3500.threetrios.adapter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.provider.view.ViewListener;
import cs3500.threetrios.view.GameEventListener;
import cs3500.threetrios.view.GameView;

/**
 * Adapter to bridge the provider's view with the existing GameView interface.
 */
public class ViewAdapter implements GameView {
  private final cs3500.threetrios.provider.view.ThreeTriosView providerView;
  private final GamePlayer viewPlayer;

  /**
   * Constructs a {@code ViewAdapter} to wrap the provider's view and associate it
   * with a specific player.
   *
   * @param providerView the provider's view implementation
   * @param viewPlayer   the player (RED or BLUE) associated with this view
   */
  public ViewAdapter(cs3500.threetrios.provider.view.ThreeTriosView providerView,
                     GamePlayer viewPlayer) {
    this.providerView = providerView;
    this.viewPlayer = viewPlayer;
  }

  @Override
  public void addGameEventListener(GameEventListener listener) {
    // Create adapter and add it to provider view
    ViewListener viewListener = new GameEventListenerAdapter(listener, viewPlayer);
    providerView.addViewListener(viewListener);
  }

  @Override
  public void updateView() {
    providerView.refresh();
  }

  /**
   * Resets the view to the initial game state.
   * Useful for starting a new game.
   */
  @Override
  public void resetView() {
    // N/A not needed.
  }

  @Override
  public void makeVisible() {
    providerView.makeVisible();
  }

  @Override
  public void displayMessage(String message) {
    JFrame frame = (JFrame) providerView;
    JOptionPane.showMessageDialog(frame, message, "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void setViewEnabled(boolean enabled) {
    providerView.setInputEnabled(enabled);
  }

  @Override
  public void showWinner(String winner) {
    JFrame frame = (JFrame) providerView;
    JOptionPane.showMessageDialog(frame, "Winner: " + winner, "Game Over",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void updateActivePlayer(GamePlayer currentPlayer) {
    providerView.updateStatus("Current active player: " + currentPlayer.getColor());
  }
}
