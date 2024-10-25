package cs3500.threetrios.model;

public class Grid {

  private Cell[][] grid;
  private int rows;
  private int cols;

  public Grid(int row, int cols) {
    this.rows = row;
    this.cols = cols;
    this.grid = new Cell[row][cols];
  }

  public Cell getCell(int row, int col) {
    return this.grid[row][col];
  }

  public void setCell(int row, int col, Cell cell) {
    this.grid[row][col] = cell;
  }
}
