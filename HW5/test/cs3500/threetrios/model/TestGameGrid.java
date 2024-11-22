package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests all the methods in the grid class.
 */
public class TestGameGrid {
  Grid grid;

  @Test
  public void testValidGameGrid() {
    Grid testGrid = new Grid(3, 3);
    assertEquals(testGrid.getRows(), 3);
  }

  @Test
  public void testInvalidGameGrid() {
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
        () -> new Grid(-1, 3));
    assertTrue(exception1.getMessage().contains("row or cols must be greater than 0"));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
        () -> new Grid(3, 0));
    assertTrue(exception2.getMessage().contains("row or cols must be greater than 0"));
  }

  /**
   * Sets up a new grid before each run of the tests.
   */
  @Before
  public void setUp() {
    grid = new Grid(5, 5);
    Cell cell00 = new Cell(CellType.CARD_CELL);
    Cell hole11 = new Cell(CellType.HOLE_CELL);
    grid.setCell(0, 0, cell00);
    grid.setCell(1, 1, hole11);
  }

  @Test
  public void testGetValidCell() {
    Cell cell1 = new Cell(CellType.CARD_CELL);
    assertEquals(grid.getCell(0, 0).getCellType(), cell1.getCellType());
  }

  @Test
  public void testGetCellForOutOfBounds() {
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
        () -> grid.getCell(-1, 0));
    assertTrue(exception1.getMessage().contains("Invalid row or column"));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
        () -> grid.getCell(0, -1));
    assertTrue(exception2.getMessage().contains("Invalid row or column"));
    IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class,
        () -> grid.getCell(10, 0));
    assertTrue(exception3.getMessage().contains("Invalid row or column"));
    IllegalArgumentException exception4 = assertThrows(IllegalArgumentException.class,
        () -> grid.getCell(0, 10));
    assertTrue(exception4.getMessage().contains("Invalid row or column"));
  }

  @Test
  public void testSetCell() {
    Cell cell01 = new Cell(CellType.CARD_CELL);
    grid.setCell(0, 1, cell01);
    assertEquals(grid.getCell(0, 1).getCellType(), cell01.getCellType());
  }

  @Test
  public void testSetCellForOutOfBounds() {
    Cell cell01 = new Cell(CellType.CARD_CELL);
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
        () -> grid.setCell(-1, 0, cell01));
    assertTrue(exception1.getMessage().contains("Invalid row or column"));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
        () -> grid.setCell(0, -1, cell01));
    assertTrue(exception2.getMessage().contains("Invalid row or column"));
    IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class,
        () -> grid.setCell(10, 0, cell01));
    assertTrue(exception3.getMessage().contains("Invalid row or column"));
    IllegalArgumentException exception4 = assertThrows(IllegalArgumentException.class,
        () -> grid.setCell(0, 10, cell01));
    assertTrue(exception4.getMessage().contains("Invalid row or column"));
  }

  @Test
  public void testSetUpAdjacentCell() {
    Grid grid = new Grid(2, 2);
    Cell cell00 = new Cell(CellType.CARD_CELL);
    Cell cell01 = new Cell(CellType.CARD_CELL);
    Cell cell10 = new Cell(CellType.HOLE_CELL);
    Cell cell11 = new Cell(CellType.CARD_CELL);

    // C C
    // X C
    grid.setCell(0, 0, cell00);
    grid.setCell(0, 1, cell01);
    grid.setCell(1, 0, cell10);
    grid.setCell(1, 1, cell11);
    grid.setupAdjacentCells();

    assertEquals(cell01, cell00.getAdjacentCells().get(Direction.EAST));
    assertEquals(cell10, cell00.getAdjacentCells().get(Direction.SOUTH));
    assertEquals(cell00.getAdjacentCells().get(Direction.SOUTH).getCellType(), CellType.HOLE_CELL);
    assertNull(cell00.getAdjacentCells().get(Direction.NORTH));
    assertNull(cell00.getAdjacentCells().get(Direction.WEST));

    assertEquals(cell00, cell01.getAdjacentCells().get(Direction.WEST));
    assertEquals(cell11, cell01.getAdjacentCells().get(Direction.SOUTH));
    assertNull(cell01.getAdjacentCells().get(Direction.NORTH));
    assertNull(cell01.getAdjacentCells().get(Direction.EAST));

    assertEquals(cell11, cell10.getAdjacentCells().get(Direction.EAST));
    assertEquals(cell00, cell10.getAdjacentCells().get(Direction.NORTH));
    assertNull(cell10.getAdjacentCells().get(Direction.SOUTH));
    assertNull(cell10.getAdjacentCells().get(Direction.WEST));

    assertEquals(cell10, cell11.getAdjacentCells().get(Direction.WEST));
    assertEquals(cell01, cell11.getAdjacentCells().get(Direction.NORTH));
    assertNull(cell11.getAdjacentCells().get(Direction.SOUTH));
    assertNull(cell11.getAdjacentCells().get(Direction.EAST));
  }

  @Test
  public void testGetRows() {
    assertEquals(grid.getRows(), 5);
  }

  @Test
  public void testGetColumns() {
    assertEquals(grid.getCols(), 5);
  }

  //calculate cards, not cardCells
  @Test
  public void testCalculateCardCells() {
    assertEquals(grid.calculateCardCells(), 1);
  }

  @Test
  public void testFindCellPosition() {
    Grid grid = new Grid(3, 3);
    Cell findCell = new Cell(CellType.CARD_CELL);
    grid.setCell(0, 0, findCell);
    assertArrayEquals(grid.findCellPosition(findCell), new int[]{0, 0});
  }

}
