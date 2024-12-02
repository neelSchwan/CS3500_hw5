package cs3500.threetrios.provider.model;

/**
 * A generic card with values that correspond to directions.
 */
public interface Card {

  /**
   * Returns the integer value that the card has at the specified direction.
   *
   * @param d the direction to check
   * @return the value in the given direction.
   */
  int getValueOf(Direction d);

  /**
   * Returns the name identifier of the card.
   *
   * @return the name as a String.
   */
  String getName();

}
