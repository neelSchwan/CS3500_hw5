package cs3500.threetrios.model;

/**
 * Card representation for the three-trios game.
 * A card in this game has a name, and 4 attack values corresponding to each cardinal direction.
 */
public interface Card {

  /**
   * Gets the name for a certain card.
   *
   * @return valid name for specified card.
   */
  String getName();

  /**
   * Gets the attack value of the specified direction of a card.
   *
   * @param direction direction for which to calculate the specified cards attack value.
   * @return returns attack value that corresponds with the passed in direction.
   */
  int getAttackValue(Direction direction);
}
