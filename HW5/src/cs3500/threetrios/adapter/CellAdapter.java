package cs3500.threetrios.adapter;

import cs3500.threetrios.model.GameCard;
import cs3500.threetrios.model.GameCell;
import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.Player;

/**
 * Adapter class for adapting the provided Cell class into our own, to make it work with our view
 * implementation.
 */
public class CellAdapter implements Cell {
  private final GameCell gameCell;

  /**
   * Constructor for creating a cellAdapter, this takes in our GameCell interface.
   * The field in this class allows for their 'cell' to be converted into ours.
   *
   * @param gameCell our cell interface.
   */
  public CellAdapter(GameCell gameCell) {
    this.gameCell = gameCell;
  }

  @Override
  public void setCard(Card card, Player owner) { //provider card, provider player.
    if (!isCardCell()) {
      throw new IllegalArgumentException("This is a hole cell");
    }
    if (hasCard()) {
      throw new IllegalArgumentException("Cell already has a card");
    }
    gameCell.placeCard(
            new GameCard(
                    "Card",
                    card.getValueOf(Direction.NORTH),
                    card.getValueOf(Direction.SOUTH),
                    card.getValueOf(Direction.EAST),
                    card.getValueOf(Direction.WEST)
            ),
            convertPlayer(owner)
    );
  }

  @Override
  public void setOwner(Player owner) {
    gameCell.setOwner(convertPlayer(owner));
  }

  @Override
  public String getOwnerName() {
    GamePlayer owner = gameCell.getOwner();
    return owner == null ? "" : owner.getColor().toString();
  }

  @Override
  public int getCardValueOf(Direction d) {
    if (!hasCard()) {
      throw new IllegalStateException("Cell has no card");
    }
    return gameCell.getCard().getAttackValue(convertDirection(d));
  }

  @Override
  public Card getCardCopy() {
    if (!hasCard()) {
      return null;
    }
    cs3500.threetrios.model.Card yourCard = gameCell.getCard();
    return new CardAdapter(yourCard);
  }

  @Override
  public int directionalCompareTo(Direction d, Cell other) {
    if (!hasCard() || !other.hasCard()) {
      throw new IllegalStateException("Cannot compare cells without cards");
    }

    int thisValue = getCardValueOf(d);
    int otherValue = other.getCardValueOf(getOppositeDirection(d));

    return Integer.compare(thisValue, otherValue);
  }

  @Override
  public boolean isOpenForPlay() {
    return isCardCell() && !hasCard();
  }

  @Override
  public boolean hasCard() {
    return gameCell.isOccupied();
  }

  @Override
  public boolean isCardCell() {
    return !gameCell.isHole();
  }

  private GamePlayer convertPlayer(Player providerPlayer) {
    // Get the current owner of this cell
    GamePlayer currentOwner = gameCell.getOwner();

    // If there's no owner, we can't convert
    if (currentOwner == null) {
      throw new IllegalStateException("No owner found for cell");
    }

    // Check if the provider's player color matches our owner's color
    boolean isMatchingColor = (providerPlayer == Player.RED
            && currentOwner.getColor() == cs3500.threetrios.model.Player.RED)
            || (providerPlayer == Player.BLUE
            && currentOwner.getColor() == cs3500.threetrios.model.Player.BLUE);

    if (isMatchingColor) {
      return currentOwner;
    }

    throw new IllegalStateException("Could not find player instance for " + providerPlayer);
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

  private Direction getOppositeDirection(Direction d) {
    switch (d) {
      case NORTH:
        return Direction.SOUTH;
      case SOUTH:
        return Direction.NORTH;
      case EAST:
        return Direction.WEST;
      case WEST:
        return Direction.EAST;
      default:
        throw new IllegalArgumentException("Invalid direction");
    }
  }
}