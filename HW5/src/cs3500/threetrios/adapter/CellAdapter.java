import cs3500.threetrios.model.GameCard;
import cs3500.threetrios.model.GameCell;
import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.Player;

public class CellAdapter implements Cell {
  private final GameCell gameCell;

  public CellAdapter(GameCell gameCell) {
    this.gameCell = gameCell;
  }

  @Override
  public void setCard(Card card, Player owner) {
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
            convertToYourPlayer(owner)
    );
  }

  @Override
  public void setOwner(Player owner) {
    gameCell.setOwner(convertToYourPlayer(owner));
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
    return gameCell.getCard().getAttackValue(convertToYourDirection(d));
  }

  @Override
  public Card getCardCopy() {
    if (!hasCard()) {
      return null;
    }
    cs3500.threetrios.model.Card yourCard = gameCell.getCard();
    return new Card(
            yourCard.getAttackValue(cs3500.threetrios.model.Direction.NORTH),
            yourCard.getAttackValue(cs3500.threetrios.model.Direction.EAST),
            yourCard.getAttackValue(cs3500.threetrios.model.Direction.SOUTH),
            yourCard.getAttackValue(cs3500.threetrios.model.Direction.WEST)
    );
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

  private cs3500.threetrios.model.Player convertToYourPlayer(Player player) {
    switch (player) {
      case RED: return cs3500.threetrios.model.Player.RED;
      case BLUE: return cs3500.threetrios.model.Player.BLUE;
      default: throw new IllegalArgumentException("Unknown player: " + player);
    }
  }

  private cs3500.threetrios.model.Direction convertToYourDirection(Direction d) {
    switch (d) {
      case NORTH: return cs3500.threetrios.model.Direction.NORTH;
      case SOUTH: return cs3500.threetrios.model.Direction.SOUTH;
      case EAST: return cs3500.threetrios.model.Direction.EAST;
      case WEST: return cs3500.threetrios.model.Direction.WEST;
      default: throw new IllegalArgumentException("Unknown direction: " + d);
    }
  }

  private Direction getOppositeDirection(Direction d) {
    switch (d) {
      case NORTH: return Direction.SOUTH;
      case SOUTH: return Direction.NORTH;
      case EAST: return Direction.WEST;
      case WEST: return Direction.EAST;
      default: throw new IllegalArgumentException("Invalid direction");
    }
  }
}