package cs3500.threetrios.adapter;

import cs3500.threetrios.provider.model.GridPos;
import cs3500.threetrios.provider.model.Direction;

public class GridPosAdapter implements GridPos {
  private final int row;
  private final int col;

  public GridPosAdapter(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public GridPos getAdjacent(Direction d) {
    switch (d) {
      case NORTH:
        return new GridPosAdapter(this.row - 1, this.col);
      case SOUTH:
        return new GridPosAdapter(this.row + 1, this.col);
      case EAST:
        return new GridPosAdapter(this.row, this.col + 1);
      case WEST:
        return new GridPosAdapter(this.row, this.col - 1);
      default:
        throw new IllegalArgumentException("Invalid direction: " + d);
    }
  }

  @Override
  public boolean isInBoundsFor(int numRows, int rowLength) {
    return this.row >= 0 && this.row < numRows && this.col >= 0 && this.col < rowLength;
  }

  @Override
  public <T> T accessArray(T[][] arr2d) {
    return arr2d[this.row][this.col];
  }
}
