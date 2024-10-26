package cs3500.threetrios.model;

/**
 * Cell representation in the threetrios game.
 * The cell holds information of the card in it, and if it's a hole or not.
 */
public class Cell {

  private Card card;
  private Player owner;
  private final CellType cellType;

  public Cell(CellType cellType) {
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
}
