package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
    this.grid[row][col] = cell;
  }

  /**
   * Gets the adjacent cells to a cell at a specified row and column.
   *
   * @param row specified row to check.
   * @param col specified col to check.
   * @return List of cells that are adjacent to the specified cell.
   */
  public Map<Direction, Cell> getAdjacentCells(int row, int col) {
    Map<Direction, Cell> adjacentCells = new EnumMap<>(Direction.class);

    if (row > 0) {
      adjacentCells.put(Direction.NORTH, getCell(row - 1, col));
    }
    if (row < grid.length - 1) {
      adjacentCells.put(Direction.SOUTH, getCell(row + 1, col));
    }
    if (col > 0) {
      adjacentCells.put(Direction.WEST, getCell(row, col - 1));
    }
    if (col < grid[0].length - 1) {
      adjacentCells.put(Direction.EAST, getCell(row, col + 1));
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


  public Position findCellPosition(Cell cell) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (grid[row][col] == cell) {
          return new Position(row, col);
        }
      }
    }
    return null; // Cell not found
  }
}
