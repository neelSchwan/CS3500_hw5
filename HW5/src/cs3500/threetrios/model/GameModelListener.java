package cs3500.threetrios.model;

/**
 * Listener interface for model events such as turn changes. THIS IS THE MODEL-STATUS INTERFACE.
 * THIS IS USED BY THE CONTROLLER FOR LISTENING TO PLAYER ACTIONS.
 */
public interface GameModelListener {
  /**
   * Called when the current player changes.
   *
   * @param currentPlayer the new current player
   */
  void onTurnChanged(GamePlayer currentPlayer);

  /**
   * Called when the game state updates like after a move is made.
   */
  void onGameStateUpdated();

  /**
   * Called when the game ends.
   *
   * @param winner the winner of the game, or null if it's a tie
   */
  void onGameOver(GamePlayer winner);
}
