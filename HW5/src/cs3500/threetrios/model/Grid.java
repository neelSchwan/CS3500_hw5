package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to hold the logic for a Grid.
 * A grid in the three-trios game is represented as 2d array of card cells or hole cells.
 */
public class Grid {

  private final Cell[][] grid;
  private final int rows;
  private final int cols;

  /**
   * Constructor for creating a grid with a specified row x col size.
   *
   * @param row  number of rows to initialize the row with.
   * @param cols number of cols to initialize the col with.
   */
  public Grid(int row, int cols) {
    this.rows = row;
    this.cols = cols;
    this.grid = new Cell[row][cols];
  }

  /**
   * Gets the cell at a current row, col position.
   *
   * @param row specified row of a Cell.
   * @param col specified column of a Cell.
   * @return Cell at the current row and column position in the grid.
   */
  public Cell getCell(int row, int col) {
    return this.grid[row][col];
  }

  /**
   * Setter to set a cell as a hole or card cell.
   *
   * @param row  specified row to set as a cell.
   * @param col  specified col to set as a cell.
   * @param cell Cell object to assign to the specified row and columns.
   */
  public void setCell(int row, int col, Cell cell) {
    this.grid[row][col] = cell;
  }

  /**
   * Gets the adjacent cells to a cell at a specified row and column.
   *
   * @param row specified row to check.
   * @param col specified col to check.
   * @return List of cells that are adjacent to the specified cell.
   */
  public List<Cell> getAdjacentCells(int row, int col) {

    List<Cell> adjacentCells = new ArrayList<>();
    // checks north cells
    if (row > 0) {
      adjacentCells.add(grid[row - 1][col]);
    }
    //south
    if (row < this.rows - 1) {
      adjacentCells.add(grid[row + 1][col]);
    }

    //west
    if (col > 0) {
      adjacentCells.add(grid[row][col - 1]);
    }

    //east
    if (col < this.cols - 1) {
      adjacentCells.add(grid[row][col + 1]);
    }
    return adjacentCells;
  }

  /**
   * Gets number of rows in the grid.
   *
   * @return int of rows.
   */
  public int getRows() {
    return this.rows;
  }

  /**
   * Gets number of cols in the grid.
   *
   * @return int of cols.
   */
  public int getCols() {
    return this.cols;
  }

  private boolean isValidPosition(int row, int col) {
    return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        sb.append(this.getCell(i, j).toString());
        if (j < this.cols - 1) {
          sb.append(" ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

}
