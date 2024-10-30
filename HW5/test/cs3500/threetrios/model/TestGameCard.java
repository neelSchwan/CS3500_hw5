package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestGameCard {

  Card card1;

  @Test
  public void testValidGameCard() {
    Card testCard = new GameCard("baron nashor", 9, 9, 9, 9);
    assertEquals(testCard.getName(), "baron nashor");
  }

  //write tests for invalid constructor

  @Before
  public void setUp() {
    card1 = new GameCard("dragon", 1, 2, 3, 4);
  }

  @Test
  public void testGetCardName() {
    assertEquals(card1.getName(), "dragon");
  }

  @Test
  public void testGetCardAttackValue() {
    assertEquals(card1.getAttackValue(Direction.EAST), 3);
    assertEquals(card1.getAttackValue(Direction.NORTH), 1);
    assertEquals(card1.getAttackValue(Direction.SOUTH), 2);
    assertEquals(card1.getAttackValue(Direction.WEST), 4);
  }

  @Test
  public void testToString() {
    assertEquals(card1.toString(), "dragon [N: 1, S: 2, E: 3, W: 4]");
  }

  @Test
  public void testEquals() {
    Card card2 = new GameCard("dragon", 1, 2, 3, 4);
    Card card3 = new GameCard("baron", 1, 2, 3, 4);
    assertEquals(card1, card2);
    assertNotEquals(card1, card3);
  }


}
