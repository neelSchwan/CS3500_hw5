package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;

public class TestGridConfigReader {
  private GridConfigReader gridConfigReader;

  @Before
  public void setUp() {
    gridConfigReader = new GridConfigReader();
  }

  @Test
  public void testReadingGridFromFileWorks() {
    Grid grid = gridConfigReader.readGridFromFile("src" + File.separator
            +"resources" + File.separator +"GridDb.txt");

    assertEquals(grid.getRows(), 5);
    assertEquals(grid.getCols(), 7);
    assertEquals(grid.getCols() * grid.getRows(), 35);
  }

  @Test
  public void testReadingCardDbWhenFileDoesntExist() {
    RuntimeException exception = assertThrows(RuntimeException.class,
            () -> gridConfigReader.readGridFromFile("src" + File.separator
                    + "resources" + File.separator + "GridDb2.txt"));
    assertTrue(exception.getMessage().contains("Issue when reading file: "));
  }

  @Test
  public void testReadingGridWhenFileIsEmpty() {
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> gridConfigReader.readGridFromFile("src" + File.separator + "resources"
                    + File.separator + "EmptyGridDb.txt"));
    assertTrue(exception.getMessage().contains("File must have some valid data."));
  }

  @Test
  public void testReadingGridWhenFilenameIsEmpty() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> gridConfigReader.readGridFromFile(""));
    assertTrue(exception.getMessage().contains("File name cannot be null or empty"));
  }

  @Test
  public void testReadingGridFromFileWhenFilenameIsNull() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> gridConfigReader.readGridFromFile(null));
    assertTrue(exception.getMessage().contains("File name cannot be null or empty"));
  }

  @Test
  public void testReadingGridWhenFilenameIsBlank() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> gridConfigReader.readGridFromFile("  "));
    assertTrue(exception.getMessage().contains("File name cannot be blank"));
  }


}
