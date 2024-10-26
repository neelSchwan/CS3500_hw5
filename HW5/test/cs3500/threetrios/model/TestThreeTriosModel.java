package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.view.ThreeTriosGameView;
import cs3500.threetrios.view.ThreeTriosView;

public class TestThreeTriosModel {

  @Test(expected = IllegalStateException.class)
  public void testStartGameWithInvalidDeck() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();
    List<Card> deck = cardConfigReader.readCards("src/resources/EmptyCardDb.txt");
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");
    System.out.println(grid.calculateCardCells());
    Map<Player, List<Card>> hands = new HashMap<>();
    hands.put(Player.RED, new ArrayList<>());
    hands.put(Player.BLUE, new ArrayList<>());
    GameModel model = new ThreeTriosModel(grid, hands, deck);
    model.startGame(0);
  }

  @Test
  public void testStartGameWithValidDeck() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();
    List<Card> deck = cardConfigReader.readCards("src/resources/CardDb.txt"); // 30 cards
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");
    Map<Player, List<Card>> hands = new HashMap<>();

    hands.put(Player.RED, new ArrayList<>());
    hands.put(Player.BLUE, new ArrayList<>());
    GameModel model = new ThreeTriosModel(grid, hands, deck);
    model.startGame(0);

    ThreeTriosView view = new ThreeTriosGameView(model);
    System.out.println(view.render(model));
    Assert.assertEquals(model.getCurrentPlayer(), Player.RED); // start as red
    Assert.assertEquals(model.getPlayerHand(Player.RED).size(), 8); // 8 because (N+1)/2, N=15

    model.placeCard(0, 0, hands.get(Player.RED).get(0)); // place card as red
    System.out.println(view.render(model));
    Assert.assertEquals(model.getCurrentPlayer(), Player.BLUE); // player swaps to blue
    Assert.assertEquals(model.getPlayerHand(Player.RED).size(), 7); // red hand decrease
    Assert.assertEquals(model.getPlayerHand(Player.BLUE).size(), 8); // blue hand is 8.
  }

  @Test(expected = IllegalStateException.class)
  public void testOperationsWhenGameHasNotStarted() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();
    List<Card> deck = cardConfigReader.readCards("src/resources/CardDb.txt"); // 30 cards
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");
    Map<Player, List<Card>> hands = new HashMap<>();

    hands.put(Player.RED, new ArrayList<>());
    hands.put(Player.BLUE, new ArrayList<>());
    GameModel model = new ThreeTriosModel(grid, hands, deck);

    hands.get(Player.RED).add(deck.get(0)); // hand needs to have 1 card before placing
    model.placeCard(0, 0, hands.get(Player.RED).get(0));
    model.switchTurn();
  }

}
