package cs3500.threetrios.adapter;

import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.model.ReadOnlyThreeTrios;
import cs3500.threetrios.provider.view.ThreeTriosView;
import cs3500.threetrios.provider.view.ViewListener;
import cs3500.threetrios.view.GameEventListener;
import cs3500.threetrios.view.GameView;

public class ProviderViewAdapter implements GameView {

  private final ThreeTriosView providerView;
  private GameEventListener gameEventListener;

  public ProviderViewAdapter(ThreeTriosView providerView) {
    this.providerView = providerView;

    this.providerView.addViewListener(new ViewListener() {
      @Override
      public void onCardSelected(int cardIndex) {
        if (gameEventListener != null) {
          gameEventListener.onCardSelected(cardIndex, null);
        }
      }

      @Override
      public void onPosSelected(int row, int col) {
        if (gameEventListener != null) {
          // Notify the GameEventListener
          gameEventListener.onCellClicked(row, col);
        }
      }
    });
  }

  @Override
  public void updateView() {
    providerView.refresh();
  }

  @Override
  public void resetView() {
    throw new UnsupportedOperationException("Reset is not supported by the provider view.");
  }

  @Override
  public void showWinner(String winner) {
    providerView.showGameOver(winner);
  }

  @Override
  public void addGameEventListener(GameEventListener listener) {
    this.gameEventListener = listener;
  }

  @Override
  public void makeVisible() {
    providerView.makeVisible();
  }

  @Override
  public void displayMessage(String message) {
    providerView.showError(message);
  }

  @Override
  public void setViewEnabled(boolean enabled) {
    providerView.setInputEnabled(enabled);
  }

  @Override
  public void updateActivePlayer(GamePlayer currentPlayer) {
    // Convert GamePlayer to the provider's Player enum
    Player providerPlayer;
    if (currentPlayer.getColor() == cs3500.threetrios.model.Player.RED) {
      providerPlayer = Player.RED;
    } else {
      providerPlayer = Player.BLUE;
    }

    providerView.updateStatus("Current turn: " + providerPlayer);
  }

//  @Override
//  public void highlightCard(int cardIndex, cs3500.threetrios.model.Player player) {
//    Player providerPlayer = (player == cs3500.threetrios.model.Player.RED)
//            ? Player.RED
//            : Player.BLUE;
//
//    providerView.highlightSelectedCard(cardIndex, providerPlayer);
//  }
}
