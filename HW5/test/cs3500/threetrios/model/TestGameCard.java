package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class TestGameCard {

  Card card1;

  @Test
  public void testValidGameCard() {
    Card testCard = new GameCard("baron nashor", 9, 9, 9, 9);
    assertEquals(testCard.getName(), "baron nashor");
  }

  @Test
  public void testInvalidNameForGameCard() {
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard(null, 9, 9, 9, 9));
    assertTrue(exception1.getMessage().contains("Name cannot be null or empty"));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("", 9, 9, 9, 9));
    assertTrue(exception2.getMessage().contains("Name cannot be null or empty"));
    IllegalArgumentException exception3 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("  ", 9, 9, 9, 9));
    assertTrue(exception3.getMessage().contains("Name cannot be blank."));
  }

  @Test
  public void testInvalidNorthValueForGameCard() {
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("baron", -1, 9, 9, 9));
    assertTrue(exception1.getMessage().contains("North value should be between 1 and 10"));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("baron", 20, 9, 9, 9));
    assertTrue(exception2.getMessage().contains("North value should be between 1 and 10"));
  }

  @Test
  public void testInvalidSouthValueForGameCard() {
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("baron", 9, -1, 9, 9));
    assertTrue(exception1.getMessage().contains("South value should be between 1 and 10"));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("baron", 9, 30, 9, 9));
    assertTrue(exception2.getMessage().contains("South value should be between 1 and 10"));
  }

  @Test
  public void testInvalidEastValueForGameCard() {
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("baron", 9, 9, -1, 9));
    assertTrue(exception1.getMessage().contains("East value should be between 1 and 10"));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("baron", 9, 9, 20, 9));
    assertTrue(exception2.getMessage().contains("East value should be between 1 and 10"));
  }

  @Test
  public void testInvalidWestValueForGameCard() {
    IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("baron", 9, 9, 9, -1));
    assertTrue(exception1.getMessage().contains("West value should be between 1 and 10"));
    IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
            () -> new GameCard("baron", 9, 9, 9, 20));
    assertTrue(exception2.getMessage().contains("West value should be between 1 and 10"));
  }

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
