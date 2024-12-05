package cs3500.threetrios.provider.model;


import java.util.List;

/**
 * Represents a grid in a generic card game played on a board of cells.
 */
public interface Grid {

  /**
   * Sets the card of the cell at the given position in the grid to the given card
   * and assigns it the given owner.
   *
   * @param pos   a GridPos used to access teh intended object in the grid array.
   * @param card  the card to be placed.
   * @param owner the initial owner to assign to the cell.
   */
  void playCard(GridPos pos, Card card, Player owner);

  /**
   * Returns a list of some adjacent positions of the given position. Each neighbor position is only
   * included in the list if it's card strictly loses to the card at the given position.
   *
   * @param pos A valid position on the grid
   * @return A list of adjacent positions containing losing cards.
   */
  List<GridPos> getLosingNeighbors(GridPos pos);

  /**
   * Dummy docs to pass style-checker, but this gets the losing values of the cards
   * next to the current GridPos.
   *
   * @param pos position in the grid.
   * @return an array of values
   */
  int[] getLosingSurroundingValues(GridPos pos);

  /**
   * Reassigns the owner of the card-cell at the given position to the given Player.
   *
   * @param pos   The position of a card-cell to be flipped.
   * @param owner The new owner of the card-cell.
   */
  public void flipCardCellTo(GridPos pos, Player owner);

  /**
   * Returns true if all card-cells on the grid contain cards. This is still true
   * if there are hole-cells on the grid.
   *
   * @return true if all card-cells are filled. False if there are one or more empty-cells.
   */
  public boolean isSaturated();

  /**
   * Returns the number of cells in the grid that are owned by the given Player.
   *
   * @param player The player identifier to check across the grid.
   * @return an integer score for the given Player.
   */
  public int getScoreOf(Player player);

  /**
   * Returns the number of card-cells in the grid. This means that hole-cells are not counted.
   *
   * @return an integer number of card-cells.
   */
  public int getNumCardCells();

  /**
   * Returns a deeply copied of the grid state at the time the method is called.
   *
   * @return a new 2D array of Cells.
   */
  public Cell[][] getCurrentGrid();

  /**
   * Returns A Copy of the cell at the specified GridPos.
   *
   * @param pos the position of the cell to retrieve
   * @return the Cell at the given position
   * @throws IllegalArgumentException if the position is out of bounds
   */
  Cell getCell(GridPos pos);
}
