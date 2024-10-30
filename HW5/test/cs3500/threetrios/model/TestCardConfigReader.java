package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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

  @Test
  public void testReadingCardDbWhenFileDoesntExist() {
    RuntimeException exception = assertThrows(RuntimeException.class,
            () -> cardReader.readCards("src/resources/CardDb2.txt"));
    assertTrue(exception.getMessage().contains("Issue when reading file: "));
  }

  @Test
  public void testReadingCardWhenFileNameIsNull() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardReader.readCards(null));
    assertTrue(exception.getMessage().contains("File name cannot be null or empty."));
  }

  @Test
  public void testReadingCardWhenFileNameIsEmpty() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardReader.readCards(""));
    assertTrue(exception.getMessage().contains("File name cannot be null or empty."));
  }

  @Test
  public void testReadingCardWhenFileNameIsBlank() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> cardReader.readCards("  "));
    assertTrue(exception.getMessage().contains("File name cannot be blank"));
  }

  @Test
  public void testReadingCardDbWhenFileIsEmpty() {
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> cardReader.readCards("src/resources/EmptyCardDb.txt"));
    assertTrue(exception.getMessage().contains("File must have some valid data."));
  }

  @Test
  public void testReadingCardDbWhenCardIsDuplicate() {
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> cardReader.readCards("src/resources/DuplicateCard.txt"));
    assertTrue(exception.getMessage().contains("Duplicate name found, edit config!"));
  }
}
