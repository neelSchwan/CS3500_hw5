package cs3500.threetrios.provider.model;

/**
 * An enum representing one of 4 directions.
 * Used to reference the corresponding 4 values of a Card.
 * Ordered NORTH, SOUTH, EAST, WEST for iteration.
 */
public enum Direction {
  NORTH, SOUTH, EAST, WEST;

  /**
   * Returns the value that is opposite to the current direction value, according
   * to a traditional compass.
   *
   * @return the opposite value.
   */
  public Direction opposite() {
    switch (this) {
      case NORTH:
        return Direction.SOUTH;
      case SOUTH:
        return Direction.NORTH;
      case EAST:
        return Direction.WEST;
      case WEST:
        return Direction.EAST;
      default:
        throw new IllegalArgumentException("Unsupported direction!");
    }
  }
}
