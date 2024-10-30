package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestGameGrid {
  Grid grid;

  @Test
  public void testValidGameGrid() {
    Grid testGrid = new Grid(3,3);
    assertEquals(testGrid.getRows(), 3);
  }

  //tests for invalid game grid

  @Before
  public void setUp() {
    grid = new Grid(5,5);
    Cell cell00 = new Cell(CellType.CARD_CELL);
    Cell hole11 = new Cell(CellType.HOLE_CELL);
    grid.setCell(0,0, cell00);
    grid.setCell(1,1, hole11);
  }

  // should think about overriding equal for cell
  @Test
  public void testGetValidCell() {
    Cell cell1 = new Cell(CellType.CARD_CELL);
    assertEquals(grid.getCell(0,0).getCellType(), cell1.getCellType());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetCellForOutOfBounds()  {
    grid.getCell(6,6);
  }

  @Test
  public void testSetCell() {
    Cell cell01 = new Cell(CellType.CARD_CELL);
    grid.setCell(0,1, cell01);
    assertEquals(grid.getCell(0,1).getCellType(), cell01.getCellType());
  }

  //testSetCellForOutOfBounds

  //how should I test this?
  @Test
  public void testSetUpAdjacentCell() {
    Cell cell10 = new Cell(CellType.CARD_CELL);
    grid.setupAdjacentCells();
  }

  @Test
  public void testGetRows() {
    assertEquals(grid.getRows(), 5);
  }

  @Test
  public void testGetColumns() {
    assertEquals(grid.getCols(), 5);
  }

  @Test
  public void testCalculateCardCells() {
    assertEquals(grid.calculateCardCells(), 1);
  }

  // find a way
  @Test
  public void testFindCellPosition() {
    Cell findCell = new Cell(CellType.CARD_CELL);
    //assertEquals(grid.findCellPosition(findCell), new int[]{0, 0});

  }

}
