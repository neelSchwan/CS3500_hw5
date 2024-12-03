package cs3500.threetrios.model;

import java.util.Map;

/**
 * Interface for usage for Cell implementations.
 */
public interface GameCell {
  /**
   * Checks if this cell holds a card.
   *
   * @return true if the cell is occupied by a card, false otherwise
   */
  boolean isOccupied();

  /**
   * Places a card in this cell with the specified owner.
   *
   * @param card   the card to place in this cell
   * @param player the owner of the card being placed
   * @throws IllegalStateException if the cell is a hole
   */
  void placeCard(Card card, GamePlayer player);

  /**
   * Returns the card in this cell.
   *
   * @return the card in this cell, or null if the cell is empty
   */
  Card getCard();

  /**
   * Returns the type of this cell.
   *
   * @return the type of this cell as a CellType
   */
  CellType getCellType();

  /**
   * Checks if this cell is a hole.
   *
   * @return true if the cell is a hole, false if it isn't.
   */
  boolean isHole();

  /**
   * Returns the owner of the card in this cell.
   *
   * @return the player who owns the card in this cell.
   */
  GamePlayer getOwner();

  /**
   * Sets the owner of this cell's card.
   *
   * @param owner the player to set as the specified cell's owner.
   */
  void setOwner(GamePlayer owner);

  /**
   * Sets the adjacent cell in the specified direction.
   *
   * @param direction the direction of the adjacent cell.
   * @param cell      the cell adjacent to this one.
   */
  void setAdjacentCell(Direction direction, GameCell cell);

  /**
   * Returns a map of this cell's adjacent cells.
   *
   * @return a map of adjacent cells by direction.
   */
  Map<Direction, GameCell> getAdjacentCells();
}
