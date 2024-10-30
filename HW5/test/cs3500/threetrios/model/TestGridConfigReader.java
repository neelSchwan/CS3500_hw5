package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGridConfigReader {
  private GridConfigReader gridConfigReader;

  @Before
  public void setUp() {
    gridConfigReader = new GridConfigReader();
  }

  @Test
  public void testReadingGridFromFileWorks() {
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    assertEquals(grid.getRows(), 5);
    assertEquals(grid.getCols(), 7);
    assertEquals(grid.getCols() * grid.getRows(), 35);
  }

  @Test(expected = RuntimeException.class)
  public void testReadingCardDbWhenFileDoesntExist() {
    gridConfigReader.readGridFromFile("src/resources/GridDb2.txt");
//    RuntimeException exception = assertThrows(RuntimeException.class,
//            () -> cardReader.readCards("src/resources/CardDb2.txt"));
//    assertTrue(exception.getMessage().contains("Issue when reading file: "));
  }

  @Test(expected = IllegalStateException.class)
  public void testReadingGridWhenFileIsEmpty() {
    gridConfigReader.readGridFromFile("src/resources/EmptyGridDb.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingGridWhenFilenameIsEmpty() {
    gridConfigReader.readGridFromFile("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingGridFromFileWhenFilenameIsNull() {
    gridConfigReader.readGridFromFile(null);
  }
}
