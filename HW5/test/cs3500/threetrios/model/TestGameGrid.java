package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestGameGrid {

  @Test
  public void testReadingGridFromFileWorks() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

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

    //TODO: CHANGE THIS TEST
  }

  @Test
  public void testGettingAdjacentCellsWithLessThan4AdjacentCellsWorks() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    //TODO: CHANGE THIS TEST
  }
}
