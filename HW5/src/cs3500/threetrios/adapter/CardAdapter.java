package cs3500.threetrios.adapter;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Direction;

/**
 * CardAdapter class that converts a card from the ThreeTrios model
 * into the provider's `Card` interface.
 */
public class CardAdapter implements Card {

  private final cs3500.threetrios.model.Card card;

  /**
   * Constructs a CardAdapter to wrap a card from the ThreeTrios model.
   *
   * @param card the card from the model to be adapted
   */
  public CardAdapter(cs3500.threetrios.model.Card card) {
    this.card = card;
  }

  /**
   * Returns the integer value that the card has at the specified direction.
   *
   * @param d the direction to check
   * @return the value in the given direction.
   */
  @Override
  public int getValueOf(Direction d) {
    return this.card.getAttackValue(convertDirection(d));
  }

  private cs3500.threetrios.model.Direction convertDirection(Direction d) {
    switch (d) {
      case NORTH:
        return cs3500.threetrios.model.Direction.NORTH;
      case SOUTH:
        return cs3500.threetrios.model.Direction.SOUTH;
      case EAST:
        return cs3500.threetrios.model.Direction.EAST;
      case WEST:
        return cs3500.threetrios.model.Direction.WEST;
      default:
        throw new IllegalArgumentException("Unknown direction: " + d);
    }
  }

  /**
   * Returns the name identifier of the card.
   *
   * @return the name as a String.
   */
  @Override
  public String getName() {
    return card.getName();
  }
}
