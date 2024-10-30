package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class TestGameCell {

  Cell cardCell;
  Cell holeCell;
  Card card;

  @Test
  public void testValidCellConstructor() {
    Cell cell1 = new Cell(CellType.CARD_CELL);
    assertEquals(cell1.getCellType(), CellType.CARD_CELL);
  }

  @Test
  public void testInvalidCellConstructor() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new Cell(null));
    assertTrue(exception.getMessage().contains("cellType cannot be null"));
  }

  @Before
  public void SetUp() {
    cardCell = new Cell(CellType.CARD_CELL);
    holeCell = new Cell(CellType.HOLE_CELL);
    card = new GameCard("dragon", 1, 2, 3, 4);
    cardCell.placeCard(card, Player.RED);
  }

  @Test
  public void testIsOccupied() {
    assertTrue(cardCell.isOccupied());
  }

  @Test
  public void testPlaceCard() {
    Cell testCell = new Cell(CellType.CARD_CELL);
    Card testCard = new GameCard("baron nashor", 9, 9, 9, 9);
    testCell.placeCard(testCard, Player.RED);
    assertEquals(testCard, testCell.getCard());
  }

  //player?
  @Test(expected = IllegalStateException.class)
  public void testPlaceCardWithHoleCard() {
    holeCell.placeCard(card, Player.RED);
  }

  @Test
  public void testGetCard() {
    assertEquals(card, cardCell.getCard());
  }

  @Test
  public void testGetCellType() {
    assertEquals(cardCell.getCellType(), CellType.CARD_CELL);
  }

  @Test
  public void testIsHole() {
    assertTrue(holeCell.isHole());
  }

  //should we throw exception if null?
  @Test
  public void testGetOwner() {
    assertEquals(cardCell.getOwner(), Player.RED);
  }

  @Test
  public void testSetOwner() {
    cardCell.setOwner(Player.BLUE);
    assertEquals(cardCell.getOwner(), Player.BLUE);
  }

  @Test
  public void testSetAdjacentCell() {
    Cell adjacentCell = new Cell(CellType.CARD_CELL);
    cardCell.setAdjacentCell(Direction.EAST, adjacentCell);
    assertEquals(adjacentCell, cardCell.getAdjacentCells().get(Direction.EAST));
  }

  @Test
  public void testGetAdjacentCards() {
  }
}
