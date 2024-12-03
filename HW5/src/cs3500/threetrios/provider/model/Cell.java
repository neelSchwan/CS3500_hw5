package cs3500.threetrios.provider.model;

/**
 * A generic game cell with teh ability to contain a card and to be owned by a player.
 */
public interface Cell {

  /**
   * Sets the card of the cell permanently.
   *
   * @param card  The card to be played
   * @param owner The player that owns the card being played
   * @throws IllegalArgumentException If this cell is a hole cell
   * @throws IllegalArgumentException If this cell already has a card
   */
  public void setCard(Card card, Player owner);

  /**
   * Sets the owner of the card.
   *
   * @param owner The owner to set the card to
   */
  public void setOwner(Player owner);

  /**
   * Gets the name of the current owner of the card (Red or Blue).
   *
   * @return the name of the current owner
   */
  public String getOwnerName();

  /**
   * Gets the value of the specified direction.
   *
   * @param d The direction to get the value of
   * @return the int value at the given direction.
   */
  public int getCardValueOf(Direction d);

  /**
   * Returns a copy of the card contained in this Cell.
   *
   * @return a new Cell with identical data.
   */
  Card getCardCopy();

  /**
   * Compares the direction of the current cell in the specified direction towards another cell.
   *
   * @param d     The direction specified
   * @param other The other cell to compare to
   * @return 1 if this cell is bigger, 0 if they are equal, -1 if this is smaller
   */
  public int directionalCompareTo(Direction d, Cell other);

  /**
   * Returns true if the cell is a card-cell and does not yet contain a card.
   *
   * @return true if open for play, false otherwise
   */
  public boolean isOpenForPlay();

  /**
   * Gets if the current cell has a card or not.
   *
   * @return true if it has a card, false otherwise
   */
  public boolean hasCard();

  /**
   * Gets if the cell is a card cell or not.
   *
   * @return true if it is a card cell, false otherwise
   */
  public boolean isCardCell();

}
