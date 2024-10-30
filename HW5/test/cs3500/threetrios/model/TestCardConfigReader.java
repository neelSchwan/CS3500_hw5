package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the Card Config Reader.
 */
public class TestCardConfigReader {
  private final String cardDb = "src" + File.separator
          + "resources" + File.separator + "CardDb.txt";
  private final String cardDb2 = "src" + File.separator
          + "resources" + File.separator + "CardDb2.txt";
  private final String emptyCardDb = "src" + File.separator
          + "resources" + File.separator + "EmptyCardDb.txt";
  private final String duplicateCardDb = "src" + File.separator
          + "resources" + File.separator + "DuplicateCard.txt";
  private CardConfigReader cardReader;

  /**
   * Sets up a reader object before each test to test on.
   */
  @Before
  public void setUp() {
    cardReader = new CardConfigReader();
  }

  @Test
  public void testReadingCardDbFileWorks() {
    List<Card> cardDbAsList = cardReader.readCards(cardDb);
    assertEquals(cardDbAsList.size(), 30);
    assertEquals(cardDbAsList.get(0).getName(), "ahri");
    assertEquals(cardDbAsList.get(1).getName(), "twitch");
    assertEquals(cardDbAsList.get(2).getAttackValue(Direction.NORTH), 6);
  }

  @Test
  public void testReadingCardDbWhenFileDoesntExist() {
    RuntimeException exception = assertThrows(RuntimeException.class,
            () -> cardReader.readCards(cardDb2));
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
            () -> cardReader.readCards(emptyCardDb));
    assertTrue(exception.getMessage().contains("File must have some valid data."));
  }

  @Test
  public void testReadingCardDbWhenCardIsDuplicate() {
    IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> cardReader.readCards(duplicateCardDb));
    assertTrue(exception.getMessage().contains("Duplicate name found, edit config!"));
  }
}
