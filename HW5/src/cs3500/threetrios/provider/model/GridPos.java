package cs3500.threetrios.provider.model;


/**
 * Represents a position on a grid of rows and columns.
 */
public interface GridPos {

  /**
   * Returns the vertical value of this grid position.
   *
   * @return an integer representing the row index.
   */
  int getRow();

  /**
   * Returns the horizontal value of this grid position.
   *
   * @return an integer representing the index within a row.
   */
  int getCol();

  /**
   * Returns the grid position that is one unit in the given direction
   * away from this grid position.
   *
   * @param d The direction of the desired adjacent position.
   * @return a new GridPos corresponding to the adjacent position.
   */
  GridPos getAdjacent(Direction d);

  /**
   * Returns true if this grid position is valid for a 0-indexed 2d array of rows and columns
   * of the specified size.
   *
   * @param numRows The number of rows of the 2d array.
   * @param rowLength The length of a row in the 2d array.
   * @return True if in bounds, false if not.
   */
  boolean isInBoundsFor(int numRows, int rowLength);

  /**
   * Works as a replacement for arr2d[GridPos.getRow()][GridPos.getCol()]
   *
   * @param arr2d the 2d array to be accessed.
   * @return the data in the 2d Array that corresponds to theis Grid Position.
   * @param <T> the data type of this 2d Array.
   */
  <T> T accessArray(T[][] arr2d);

}
