package cs3500.threetrios.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is an implementation of the Card interface, for cards that are used during the game.
 * A card has a name and 4 attack values based on a direction (N,S,W,E)
 */
public class GameCard implements Card {

  private final String name;

  private final Map<Direction, Integer> attackValues;

  /**
   * Constructor for creating a GameCard for the three-trios game.
   *
   * @param name name to correspond to the new GameCard object being created.
   */
  public GameCard(String name, int northValue, int southValue, int eastValue, int westValue) {
    //needs invariants
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

  // Override equals method
  @Override
  public boolean equals(Object obj) {
    // Check if the same reference
    if (this == obj) {
      return true;
    }
    // Check if obj is null or not the same class
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    // Cast obj to GameCard and compare fields
    GameCard other = (GameCard) obj;
    return Objects.equals(this.name, other.name)
            && Objects.equals(this.attackValues, other.attackValues);
  }

  // Override hashCode method
  @Override
  public int hashCode() {
    return Objects.hash(name, attackValues);
  }

}
