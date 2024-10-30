package cs3500.threetrios.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to hold the logic for a Grid.
 * A grid in the three-trios game is represented as 2d array of card cells or hole cells.
 */
public class Grid {

  private final Cell[][] grid;
  private final int rows;
  private final int cols;
  private final Map<Cell, int[]> cellPositions;

  /**
   * Constructor for creating a grid with a specified row x col size.
   *
   * @param row  number of rows to initialize the row with.
   * @param cols number of cols to initialize the col with.
   */
  public Grid(int row, int cols) {
    if (row < 1 || cols < 1) {
      throw new IllegalArgumentException("row or cols must be greater than 0");
    }
    this.rows = row;
    this.cols = cols;
    this.grid = new Cell[row][cols];
    this.cellPositions = new HashMap<>();
  }

  /**
   * Gets the cell at a current row, col position.
   *
   * @param row specified row of a Cell.
   * @param col specified column of a Cell.
   * @return Cell at the current row and column position in the grid.
   */
  public Cell getCell(int row, int col) {
    if (row >= 0 && row < this.rows && col >= 0 && col < this.cols) {
      return this.grid[row][col];
    } else {
      throw new IllegalArgumentException("Invalid row or column");
    }
  }

  /**
   * Setter to set a cell as a hole or card cell.
   *
   * @param row  specified row to set as a cell.
   * @param col  specified col to set as a cell.
   * @param cell Cell object to assign to the specified row and columns.
   */
  public void setCell(int row, int col, Cell cell) {
    if (row < 0 || col < 0 || row > this.rows || col > this.cols) {
      throw new IllegalArgumentException("Invalid row or column");
    }
    this.grid[row][col] = cell;
    this.cellPositions.put(cell, new int[]{row, col});
  }

  /**
   * Sets up adjacent cells for each cell in the grid, creates connections based on
   * direction, (NORTH, SOUTH, EAST, WEST) between neighboring cells.
   * This sets up each cells adjacency for efficient access during gameplay.
   */
  public void setupAdjacentCells() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Cell cell = grid[row][col];
        if (cell != null) {
          if (row > 0) {
            cell.setAdjacentCell(Direction.NORTH, grid[row - 1][col]);
          }
          if (row < rows - 1) {
            cell.setAdjacentCell(Direction.SOUTH, grid[row + 1][col]);
          }
          if (col > 0) {
            cell.setAdjacentCell(Direction.WEST, grid[row][col - 1]);
          }
          if (col < cols - 1) {
            cell.setAdjacentCell(Direction.EAST, grid[row][col + 1]);
          }
        }
      }
    }
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

  /**
   * Calculates the number of card cells in the grid.
   * Used for checking if there is a valid deck to start the game with.
   *
   * @return int of CARD_CELLS.
   */
  public int calculateCardCells() {
    int count = 0;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Cell cell = grid[row][col];
        if (cell != null && !cell.isHole()) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Method to find a specified cell in the grid.
   *
   * @param cell specified cell to look for.
   * @return Position object of where the cell is in the grid.
   */
  public int[] findCellPosition(Cell cell) {
    return cellPositions.get(cell);
  }
}
