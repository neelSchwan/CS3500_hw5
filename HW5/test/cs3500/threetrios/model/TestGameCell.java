package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Class that holds the tests for the Cell object.
 */
public class TestGameCell {

  Cell cardCell;
  Cell holeCell;
  Card card;
  GamePlayer redPlayer;

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

  /**
   * Sets up some cells for testing.
   * Sets up a card Cell to test operations on.
   * Sets up a hole Cell to test other operations on.
   * Creates a card called 'dragon' and places it in the card cell.
   */
  @Before
  public void setUp() {
    cardCell = new Cell(CellType.CARD_CELL);
    holeCell = new Cell(CellType.HOLE_CELL);
    card = new GameCard("dragon", 1, 2, 3, 4);
    List<Card> playerHand = new ArrayList<>();
    playerHand.add(card);
    redPlayer = new HumanPlayer(Player.RED, playerHand);
    cardCell.placeCard(card, redPlayer);
  }

  @Test
  public void testIsOccupied() {
    assertTrue(cardCell.isOccupied());
  }

  @Test
  public void testPlaceCard() {
    Cell testCell = new Cell(CellType.CARD_CELL);
    Card testCard = new GameCard("baron nashor", 9, 9, 9, 9);
    List<Card> playerHand = new ArrayList<>();
    playerHand.add(testCard);
    testCell.placeCard(testCard, new HumanPlayer(Player.RED, playerHand));
    assertEquals(testCard, testCell.getCard());
  }

  //player?
  @Test(expected = IllegalStateException.class)
  public void testPlaceCardWithHoleCard() {
    holeCell.placeCard(card, new HumanPlayer(Player.RED, List.of()));
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
    assertEquals(cardCell.getOwner(), redPlayer);
  }

  @Test
  public void testSetOwner() {
    GamePlayer bluePlayer = new HumanPlayer(Player.BLUE, List.of());
    cardCell.setOwner(bluePlayer);
    assertEquals(cardCell.getOwner(), bluePlayer);
  }

  @Test
  public void testSetAdjacentCell() {
    Cell adjacentCell = new Cell(CellType.CARD_CELL);
    cardCell.setAdjacentCell(Direction.EAST, adjacentCell);
    assertEquals(adjacentCell, cardCell.getAdjacentCells().get(Direction.EAST));
  }

}
