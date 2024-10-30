package cs3500.threetrios.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Cell representation in the threetrios game.
 * The cell holds information of the card in it, and if it's a hole or not.
 */
public class Cell {

  private Card card;
  private Player owner;
  private final CellType cellType;
  private final Map<Direction, Cell> adjacentCells = new EnumMap<>(Direction.class);

  public Cell(CellType cellType) {
    //INVARIANT: cell type cannot be null
    if (cellType == null) {
      throw new IllegalArgumentException("cellType cannot be null");
    }
    this.cellType = cellType;
  }

  /**
   * Checks if the cell has a card.
   *
   * @return true or false depending on if the cell holds a card.
   * We will initialize holes as null values in the grid,
   */
  public boolean isOccupied() {
    return cellType == CellType.CARD_CELL && card != null;
  }

  /**
   * Place a card in the specified cell.
   *
   * @param card   specified card to place.
   * @param player player who owns the card being placed.
   */
  public void placeCard(Card card, Player player) {
    //INVARIANT: cannot play card on hole cell
    if (cellType == CellType.HOLE_CELL) {
      throw new IllegalStateException("Cannot place a card in a hole");
    }

    this.card = card;
    this.owner = player;
  }

  /**
   * Gets card in the cell.
   *
   * @return Card object that's currently in the cell.
   */
  public Card getCard() {
    return card;
  }

  public CellType getCellType() {return cellType; };

  /**
   * Checks if the cell is a hole.
   *
   * @return true if the specified cell is a hole.
   */
  public boolean isHole() {
    return cellType == CellType.HOLE_CELL;
  }

  /**
   * Gets owner of the current cells card.
   *
   * @return player object who owns the cells card.
   */
  public Player getOwner() {
    return owner;
  }

  @Override
  public String toString() {
    return cellType.toString().substring(0, 1).toUpperCase();
  }

  /**
   * Sets the owner of current cell to a player.
   *
   * @param owner Player (RED or BLUE) to set the cell's owner to.
   */
  public void setOwner(Player owner) {
    this.owner = owner;
  }

  public void setAdjacentCell(Direction direction, Cell cell) {
    adjacentCells.put(direction, cell);
  }

  public Map<Direction, Cell> getAdjacentCells() {
    return adjacentCells;
  }


}
