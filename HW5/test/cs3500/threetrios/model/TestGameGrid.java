package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestGameGrid {

  @Test
  public void testReadingGridFromFileWorks() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    System.out.println(grid.toString());
    Assert.assertEquals(grid.getRows(), 5);
    Assert.assertEquals(grid.getCols(), 7);
    Assert.assertEquals(grid.getCols() * grid.getRows(), 35);
  }

  @Test(expected = IllegalStateException.class)
  public void testReadingGridFromFileThrowsExceptionWithEmptyGrid() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    gridConfigReader.readGridFromFile("src/resources/EmptyGridDb.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingWithEmptyFileNameThrowsExceptionWithEmptyGrid() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    gridConfigReader.readGridFromFile("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingWithNullFileNameThrowsExceptionWithEmptyGrid() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    gridConfigReader.readGridFromFile(null);
  }

  @Test
  public void testGettingAdjacentCellsWith4AdjacentCellsWorks() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    List<Cell> adjCellsList = grid.getAdjacentCells(1, 1);
    Assert.assertEquals(adjCellsList.size(), 4);
    Assert.assertEquals(adjCellsList.get(0), grid.getCell(0, 1));
    Assert.assertEquals(adjCellsList.get(1), grid.getCell(2, 1));
    Assert.assertEquals(adjCellsList.get(2), grid.getCell(1, 0));
    Assert.assertEquals(adjCellsList.get(3), grid.getCell(1, 2));
  }

  @Test
  public void testGettingAdjacentCellsWithLessThan4AdjacentCellsWorks() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    List<Cell> adjCellsList = grid.getAdjacentCells(0, 0);
    Assert.assertEquals(adjCellsList.size(), 2); // since only two cells adjacent to leftmost
  }
}
