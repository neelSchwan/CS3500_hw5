package cs3500.threetrios.model;

import java.util.EnumMap;
import java.util.Map;

public class GameCard implements Card {

  private final String name;

  private final Map<Direction, Integer> attackValues;

  /**
   * Constructor for creating a GameCard for the three-trios game.
   *
   * @param name name to correspond to the new GameCard object being created.
   */
  public GameCard(String name, int northValue, int southValue, int eastValue, int westValue) {
    this.name = name;
    this.attackValues = new EnumMap<>(Direction.class);
    attackValues.put(Direction.NORTH, northValue);
    attackValues.put(Direction.SOUTH, southValue);
    attackValues.put(Direction.EAST, eastValue);
    attackValues.put(Direction.WEST, westValue);
  }

  /**
   * Gets the name for a certain card.
   *
   * @return valid name for specified card.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the attack value of the specified direction of a card.
   *
   * @param direction direction for which to calculate the specified cards attack value.
   * @return returns attack value that corresponds with the passed in direction.
   */
  @Override
  public int getAttackValue(Direction direction) {
    return attackValues.get(direction);
  }

  @Override
  public String toString() {
    return String.format(
            "%s [N: %d, S: %d, E: %d, W: %d]",
            name,
            attackValues.get(Direction.NORTH),
            attackValues.get(Direction.SOUTH),
            attackValues.get(Direction.EAST),
            attackValues.get(Direction.WEST)
    );
  }
}
