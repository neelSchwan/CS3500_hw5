package cs3500.threetrios.provider.model;

/**
 * The interface for the model listener.
 */
public interface ModelListener {

  /**
   * Notifies the listener when the turn has changed.
   *
   * @param currentPlayer current player
   */
  void onTurnChanged(Player currentPlayer);

  /**
   * Notifies the listener when the game is over.
   *
   * @param winner       winning player
   * @param winningScore the winning score
   */
  void onGameOver(Player winner, int winningScore);
}
