package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCardConfigReader {
  private CardConfigReader cardReader;

  @Before
  public void setUp() {
    cardReader = new CardConfigReader();
  }

  @Test
  public void testReadingCardDbFileWorks() {
    List<Card> cardDbAsList = cardReader.readCards("src/resources/CardDb.txt");
    assertEquals(cardDbAsList.size(), 30);
    assertEquals(cardDbAsList.get(0).getName(), "ahri");
    assertEquals(cardDbAsList.get(1).getName(), "twitch");
    assertEquals(cardDbAsList.get(2).getAttackValue(Direction.NORTH), 6);
  }

  @Test(expected = RuntimeException.class)
  public void testReadingCardDbWhenFileDoesntExist() {
    cardReader.readCards("src/resources/CardDb2.txt");
//    RuntimeException exception = assertThrows(RuntimeException.class,
//            () -> cardReader.readCards("src/resources/CardDb2.txt"));
//    assertTrue(exception.getMessage().contains("Issue when reading file: "));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingCardWhenFileNameIsNull() {
    cardReader.readCards(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingCardWhenFileNameIsEmpty() {
    cardReader.readCards("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingCardWhenFileNameIsBlank() {
    cardReader.readCards("  ");
  }

  @Test(expected = IllegalStateException.class)
  public void testReadingCardDbWhenFileIsEmpty() {
    cardReader.readCards("src/resources/EmptyCardDb.txt");
  }

  @Test(expected = IllegalStateException.class)
  public void testReadingCardDbWhenCardIsDuplicate() {
    cardReader.readCards("src/resources/DuplicateCard.txt");
  }




}
