package cs3500.threetrios.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a cell on the Three Trios game grid.
 * A cell can either hold a card or be a hole where no card can be placed.
 */
public class Cell {

  private Card card;
  private GamePlayer owner;
  private final CellType cellType;
  private final Map<Direction, Cell> adjacentCells = new EnumMap<>(Direction.class);

  /**
   * Constructs a Cell with the specified cell type.
   *
   * @param cellType the type of this cell, either CARD_CELL or HOLE_CELL
   * @throws IllegalArgumentException if cellType is null
   */
  public Cell(CellType cellType) {
    if (cellType == null) {
      throw new IllegalArgumentException("cellType cannot be null");
    }
    this.cellType = cellType;
  }

  /**
   * Checks if this cell holds a card.
   *
   * @return true if the cell is occupied by a card, false otherwise
   */
  public boolean isOccupied() {
    return cellType == CellType.CARD_CELL && card != null;
  }

  /**
   * Places a card in this cell with the specified owner.
   *
   * @param card   the card to place in this cell
   * @param player the owner of the card being placed
   * @throws IllegalStateException if the cell is a hole
   */
  public void placeCard(Card card, GamePlayer player) {
    if (cellType == CellType.HOLE_CELL) {
      throw new IllegalStateException("Cannot place a card in a hole");
    }
    this.card = card;
    this.owner = player;
  }

  /**
   * Returns the card in this cell.
   *
   * @return the card in this cell, or null if the cell is empty
   */
  public Card getCard() {
    return card;
  }

  /**
   * Returns the type of this cell.
   *
   * @return the type of this cell as a CellType
   */
  public CellType getCellType() {
    return cellType;
  }

  /**
   * Checks if this cell is a hole.
   *
   * @return true if the cell is a hole, false if it isn't.
   */
  public boolean isHole() {
    return cellType == CellType.HOLE_CELL;
  }

  /**
   * Returns the owner of the card in this cell.
   *
   * @return the player who owns the card in this cell.
   */
  public GamePlayer getOwner() {
    return owner;
  }

  /**
   * Sets the owner of this cell's card.
   *
   * @param owner the player to set as the specified cell's owner.
   */
  public void setOwner(GamePlayer owner) {
    this.owner = owner;
  }

  /**
   * Sets the adjacent cell in the specified direction.
   *
   * @param direction the direction of the adjacent cell.
   * @param cell      the cell adjacent to this one.
   */
  public void setAdjacentCell(Direction direction, Cell cell) {
    adjacentCells.put(direction, cell);
  }

  /**
   * Returns a map of this cell's adjacent cells.
   *
   * @return a map of adjacent cells by direction.
   */
  public Map<Direction, Cell> getAdjacentCells() {
    return adjacentCells;
  }
}
