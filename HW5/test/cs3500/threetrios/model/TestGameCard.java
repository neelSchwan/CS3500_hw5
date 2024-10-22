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
    List<Card> cardDbAsList = cardReader.readCards("src/cs3500/threetrios/model/CardDb.txt");
    Assert.assertEquals(cardDbAsList.size(), 3);
    Assert.assertEquals(cardDbAsList.get(0).getName(), "card1");
    Assert.assertEquals(cardDbAsList.get(1).getName(), "dragon");
    Assert.assertEquals(cardDbAsList.get(2).getAttackValue(Direction.NORTH), 12);
  }
}
