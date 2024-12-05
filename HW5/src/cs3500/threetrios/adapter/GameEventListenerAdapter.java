package cs3500.threetrios.adapter;

import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.provider.view.ViewListener;
import cs3500.threetrios.view.GameEventListener;

public class GameEventListenerAdapter implements ViewListener {
  private final GameEventListener gameEventListener;
  private final GamePlayer player;

  public GameEventListenerAdapter(GameEventListener gameEventListener, GamePlayer player) {
    this.gameEventListener = gameEventListener;
    this.player = player;
  }

  /**
   * When a player selects a card from their hand
   * the listener will receive this event.
   *
   * @param cardIndex index of card
   */
  @Override
  public void onCardSelected(int cardIndex) {
    if (gameEventListener != null) {
      gameEventListener.onCardSelected(cardIndex, player);
    }
  }

  /**
   * When a player selects a position on the board
   * to place a card, the listener will receive this
   * event.
   *
   * @param row row of position
   * @param col col of position
   */
  @Override
  public void onPosSelected(int row, int col) {
    if (gameEventListener != null) {
      gameEventListener.onCellClicked(row, col);
    }
  }
}
