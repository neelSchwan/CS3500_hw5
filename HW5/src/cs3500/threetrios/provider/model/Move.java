package cs3500.threetrios.provider.model;

/**
 * The data that makes up one "move" (a player's action on their turn). The methods should return
 * individual pieces of data that are required for a model of a game to make a move.
 */
public interface Move {

  /**
   * Returns the position on the game's grid where this move should take place.
   *
   * @return a new GridPos.
   */
  GridPos getPosition();

  /**
   * Returns the index in a player's hand of that card that should be played on this move.
   *
   * @return an int representing an index in a 0-based hand of cards.
   */
  int getCardIdxInHand();

}
