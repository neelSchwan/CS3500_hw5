package cs3500.threetrios.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestGameGrid {

  @Test
  public void testReadingGridFromFileWorks() {
    GridConfigReader gridConfigReader = new GridConfigReader();
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    assertEquals(grid.getRows(), 5);
    assertEquals(grid.getCols(), 7);
    assertEquals(grid.getCols() * grid.getRows(), 35);
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
}
