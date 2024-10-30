package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.view.ThreeTriosGameView;
import cs3500.threetrios.view.ThreeTriosView;

public class TestThreeTriosModel {

  private GameModel model;
  private List<Card> deck;
  private Grid grid;
  private Map<Player, List<Card>> hands;

  @Before
  public void setUp() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    deck = cardConfigReader.readCards("src/resources/CardDb.txt");
    grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    hands = new HashMap<>();
    hands.put(Player.RED, new ArrayList<>());
    hands.put(Player.BLUE, new ArrayList<>());

    model = new ThreeTriosModel(grid, hands, deck);
  }

  @Test
  public void testValidConstructor() {
    GameModel model = new ThreeTriosModel(grid, hands, deck);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullGrid() {
    GameModel model = new ThreeTriosModel(null, hands, deck);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullHands() {
    GameModel model = new ThreeTriosModel(grid, null, deck);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullDeck() {
    GameModel model = new ThreeTriosModel(grid, hands, null);
  }

  @Test
  public void testStartGameWithValidDeck() {
    model.startGame(0);

    ThreeTriosView view = new ThreeTriosGameView(model);
    System.out.println(view.render(model));

    Assert.assertEquals(Player.RED, model.getCurrentPlayer());
    Assert.assertEquals(8, model.getPlayerHand(Player.RED).size()); // 8 (N+1)/2, N=15

    model.placeCard(0, 0, model.getPlayerHand(Player.RED).get(0));
    System.out.println(view.render(model));

    Assert.assertEquals(Player.BLUE, model.getCurrentPlayer()); // player swaps to blue
    Assert.assertEquals(7, model.getPlayerHand(Player.RED).size()); // red hand decreases
    Assert.assertEquals(8, model.getPlayerHand(Player.BLUE).size()); // blue hand is still 8
  }

  @Test(expected = IllegalStateException.class)
  public void testOperationsWhenGameHasNotStarted() {
    hands.get(Player.RED).add(deck.get(0));

    model.placeCard(0, 0, hands.get(Player.RED).get(0));

    model.switchTurn();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameWithInvalidDeck() {
    deck.clear(); // invalid deck.

    model.startGame(0); // throws exception
  }

  @Test
  public void testBattlePhase() {
    model.startGame(0);
    model.placeCard(0, 0, model.getPlayerHand(Player.RED).get(1)); // 4 on east
    model.placeCard(0, 1, model.getPlayerHand(Player.BLUE).get(0)); // 8 on west
    Assert.assertEquals(Player.BLUE, grid.getCell(0, 1).getOwner());
  }

  @Test
  public void testSwitchTurns() {
    model.startGame(0);

    Assert.assertEquals(Player.RED, model.getCurrentPlayer());

    model.placeCard(0, 0, model.getPlayerHand(Player.RED).get(0));
    Assert.assertEquals(Player.BLUE, model.getCurrentPlayer());

    model.placeCard(0, 1, model.getPlayerHand(Player.BLUE).get(0));
    Assert.assertEquals(Player.RED, model.getCurrentPlayer());
  }

  @Test
  public void testGameEndCondition() {
    model.startGame(0);
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (!grid.getCell(row, col).isHole()) {
          model.placeCard(row, col, model.getPlayerHand(model.getCurrentPlayer()).get(0));
        }
      }
    }
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testBattleSwitchesWhoOwnsCardAndCell() {
    model.startGame(0);
    model.placeCard(0, 0, model.getPlayerHand(Player.RED).get(1)); // 4 on east
    model.placeCard(0, 1, model.getPlayerHand(Player.BLUE).get(0)); // 8 on west
    Assert.assertEquals(Player.BLUE, grid.getCell(0, 0).getOwner());
    Assert.assertEquals(Player.BLUE, grid.getCell(0, 1).getOwner());
  }

  @Test
  public void testTieBetweenCellsMeansNothingHappens() {
    model.startGame(0);

    model.placeCard(0, 0, model.getPlayerHand(Player.RED).get(0)); //  on east
    model.placeCard(0, 1, model.getPlayerHand(Player.BLUE).get(0)); // 8 on west

    Assert.assertEquals(Player.RED, grid.getCell(0, 0).getOwner());
    Assert.assertEquals(Player.BLUE, grid.getCell(0, 1).getOwner());
  }

  @Test
  public void testGameTyingLogicWorks() {
    ThreeTriosView view = new ThreeTriosGameView(model);
    model.startGame(0);
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (!grid.getCell(row, col).isHole()) {
          model.placeCard(row, col, model.getPlayerHand(model.getCurrentPlayer()).get(0));
        }
      }
    }
    System.out.println(view.render(model));
    Assert.assertTrue(model.isGameOver());
    Assert.assertNull(model.getWinner()); // since we said that tie = null.
  }

  private GameModel createModelForEasyTesting() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    List<Card> deck = cardConfigReader.readCards("src/resources/CardDb.txt");
    Grid grid = gridConfigReader.readGridFromFile("src/resources/EasyTestingGridDb.txt");

    Map<Player, List<Card>> hands = new HashMap<>();
    hands.put(Player.RED, new ArrayList<>());
    hands.put(Player.BLUE, new ArrayList<>());

    return new ThreeTriosModel(grid, hands, deck);
  }

  @Test
  public void testWinningLogicWorks() {
    GameModel gameModel = createModelForEasyTesting();
    ThreeTriosView view = new ThreeTriosGameView(gameModel);
    gameModel.startGame(0);
    Assert.assertEquals(gameModel.getPlayerHand(Player.BLUE).size(), 13);
    Assert.assertEquals(gameModel.getPlayerHand(Player.RED).size(), 13);
    System.out.println(view.render(gameModel));
    for (int row = 0; row < gameModel.getGrid().getRows(); row++) {
      for (int col = 0; col < gameModel.getGrid().getCols(); col++) {
        if (!gameModel.getGrid().getCell(row, col).isHole()) {
          gameModel.placeCard(row, col, gameModel.getPlayerHand(gameModel.getCurrentPlayer()).get(0));
          System.out.println("Placed card at (" + row + ", " + col + ")");
        }
      }
    }
    System.out.println(view.render(gameModel));

    Assert.assertTrue(gameModel.isGameOver());
    Assert.assertEquals(Player.BLUE, gameModel.getWinner());
  }
}
