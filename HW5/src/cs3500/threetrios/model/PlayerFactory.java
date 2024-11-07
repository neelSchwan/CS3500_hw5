package cs3500.threetrios.model;

import java.util.List;

/**
 * Factory class for creating game player objects.
 * This will be used to create human and AI players in the future.
 */
public interface PlayerFactory {

  /**
   * Method to create a player given a color and a list of cards in the players hand.
   *
   * @param color specified color, (RED or BLUE).
   * @param hand  List of cards that belong to the player when the game starts.
   * @return a GamePlayer object of the created player.
   */
  GamePlayer createPlayer(Player color, List<Card> hand);
}
