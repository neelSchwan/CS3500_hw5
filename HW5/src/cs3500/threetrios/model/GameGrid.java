package cs3500.threetrios.model;

/**
 * Interface representing a grid in the ThreeTrios game.
 */
public interface GameGrid {

  /**
   * Gets the cell at a specified row and column position.
   *
   * @param row specified row of a Cell.
   * @param col specified column of a Cell.
   * @return Cell at the current row and column position in the grid.
   */
  GameCell getCell(int row, int col);

  /**
   * Sets a cell as a hole or card cell at a specified row and column.
   *
   * @param row  specified row to set as a cell.
   * @param col  specified col to set as a cell.
   * @param cell Cell object to assign to the specified row and columns.
   */
  void setCell(int row, int col, GameCell cell);

  /**
   * Sets up adjacent cells for each cell in the grid.
   */
  void setupAdjacentCells();

  /**
   * Gets the number of rows in the grid.
   *
   * @return int representing the number of rows.
   */
  int getRows();

  /**
   * Gets the number of columns in the grid.
   *
   * @return int representing the number of columns.
   */
  int getCols();

  /**
   * Calculates the number of card cells in the grid.
   *
   * @return int representing the number of card cells.
   */
  int calculateCardCells();

  /**
   * Finds the position of a specified cell in the grid.
   *
   * @param cell specified cell to look for.
   * @return int array containing the row and column of the cell, or null if not found.
   */
  int[] findCellPosition(GameCell cell);
}
