package cs3500.threetrios.provider.model;

/**
 * This interface is used when deciding what kind of
 * Player will be used to play.
 */
public interface PlayerType {
  /**
   * Returns the color of the player.
   *
   * @return color of player
   */
  Player getPlayerColor();

  /**
   * How the player will perform an action.
   */
  void performAction();
}
