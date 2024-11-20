package cs3500.threetrios.model;

import java.util.List;

/**
 * Class HumanPlayerFactory that is the concrete implementation of the PlayerFactory interface.
 * This class allows for creation of players through
 */
public class HumanPlayerFactory implements PlayerFactory {

  /**
   * Method to create a player given a color and a list of cards in the players hand.
   *
   * @param color specified color, (RED or BLUE).
   * @param hand  List of cards that belong to the player when the game starts.
   * @return a GamePlayer object of the created player.
   */
  @Override
  public GamePlayer createPlayer(Player color, List<Card> hand) {
    return new HumanPlayer(color, hand);
  }
}
