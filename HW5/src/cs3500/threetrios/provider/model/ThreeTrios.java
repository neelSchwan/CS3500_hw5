package cs3500.threetrios.provider.model;

/**
 * Represents a rules-keeper for a game of ThreeTrios.
 */
public interface ThreeTrios extends ReadOnlyThreeTrios, ModelFeatures {

  /**
   * Places the card at the given index in the player's hand whose turn it currently is
   * at the given position on the grid.
   *
   * @param pos     the position in the grid to place the card at.
   * @param cardIdx an index in the current players hand.
   */
  void placeCard(GridPos pos, int cardIdx);
}
