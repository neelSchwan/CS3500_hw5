package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestGameCard {


  @Test
  public void testGetCardName() {
    Card card1 = new GameCard("dragon", 1, 2, 3, 4);
    Assert.assertEquals(card1.getName(), "dragon");
  }

  @Test
  public void testGetCardAttackValue() {
    Card card1 = new GameCard("dragon", 1, 2, 3, 4);
    Assert.assertEquals(card1.getAttackValue(Direction.EAST), 3);
    Assert.assertEquals(card1.getAttackValue(Direction.NORTH), 1);
    Assert.assertEquals(card1.getAttackValue(Direction.SOUTH), 2);
    Assert.assertEquals(card1.getAttackValue(Direction.WEST), 4);
  }

  @Test
  public void testReadingCardDbFileWorks() {
    CardConfigReader cardReader = new CardConfigReader();
    List<Card> cardDbAsList = cardReader.readCards("src/resources/CardDb.txt");
    Assert.assertEquals(cardDbAsList.size(), 30);
    Assert.assertEquals(cardDbAsList.get(0).getName(), "ahri");
    Assert.assertEquals(cardDbAsList.get(1).getName(), "twitch");
    Assert.assertEquals(cardDbAsList.get(2).getAttackValue(Direction.NORTH), 6);
  }

  @Test(expected = RuntimeException.class)
  public void testReadingCardDbWhenFileDoesntExist() {
    CardConfigReader cardReader = new CardConfigReader();
    cardReader.readCards("src/resources/CardDb2.txt");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingCardWhenFileNameIsNull() {
    CardConfigReader cardReader = new CardConfigReader();
    cardReader.readCards(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadingCardWhenFileNameIsEmpty() {
    CardConfigReader cardReader = new CardConfigReader();
    cardReader.readCards("");
  }

  @Test(expected = IllegalStateException.class)
  public void testReadingCardDbWhenFileIsEmpty() {
    CardConfigReader cardReader = new CardConfigReader();
    cardReader.readCards("src/resources/EmptyCardDb.txt");
  }

  @Test(expected = IllegalStateException.class)
  public void testReadingCardDbWhenFileIsNull() {
    CardConfigReader cardReader = new CardConfigReader();
    cardReader.readCards("src/resources/DuplicateCard.txt");
  }
}
