package cs3500.threetrios.adapter;

import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.provider.view.ViewListener;
import cs3500.threetrios.view.GameEventListener;

/**
 * Adapter to convert events from the provider's listener interface
 * to our existing GameEventListener interface. This bridges the gap
 * between the provider's view and the controller by relaying
 * user interactions in the view to the game logic.
 */
public class GameEventListenerAdapter implements ViewListener {
  private final GameEventListener gameEventListener;
  private final GamePlayer player;

  /**
   * Constructs a GameEventListenerAdapter that wraps a GameEventListener
   * and associates it with a specific player.
   *
   * @param gameEventListener the internal event listener to relay provider events to.
   * @param player            the player (RED or BLUE) associated with this adapter.
   */
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
