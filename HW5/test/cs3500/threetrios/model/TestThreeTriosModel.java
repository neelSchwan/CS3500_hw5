package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Test class for the ThreeTriosModel class.
 * This class sets up a test environment for verifying the functionality of the ThreeTrios
 * game model.
 */
public class TestThreeTriosModel {

  private GameModel model;
  private List<Card> deck;
  private Grid grid;
  private PlayerFactory playerFactory;

  /**
   * Initializes the test setup with a game model, deck of cards, grid, and player hands.
   * This method runs before each test to ensure consistency between model tests.
   */
  @Before
  public void setUp() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    deck = cardConfigReader.readCards("src" + File.separator
            + "resources" + File.separator + "CardDb.txt");
    grid = gridConfigReader.readGridFromFile("src" + File.separator
            + "resources" + File.separator + "GridDb.txt");

    playerFactory = new HumanPlayerFactory();

    model = new ThreeTriosModel(grid, playerFactory, deck);
  }

  @Test
  public void testValidConstructor() {
    GameModel model = new ThreeTriosModel(grid, playerFactory, deck);
    Assert.assertNotNull("Model should not be null", model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullGrid() {
    GameModel model = new ThreeTriosModel(null, playerFactory, deck);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullHands() {
    GameModel model = new ThreeTriosModel(grid, null, deck);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullFactory() {
    GameModel model = new ThreeTriosModel(grid, playerFactory, null);
  }

  @Test
  public void testStartGameWithValidDeck() throws IOException {
    model.startGame(0);

    GamePlayer redPlayer = model.getPlayers().get(0); // Get RED player
    GamePlayer bluePlayer = model.getPlayers().get(1); // Get BLUE player

    System.out.println(redPlayer.getPlayerHand());

    Assert.assertEquals(Player.RED, redPlayer.getColor());
    Assert.assertEquals(8, redPlayer.getPlayerHand().size()); // 8 cards for RED
    Assert.assertEquals(8, bluePlayer.getPlayerHand().size()); // 8 cards for BLUE
    // Place a card for RED and check hand size decreases
    model.placeCard(0, 0, redPlayer.getPlayerHand().get(0));

    Assert.assertEquals(Player.BLUE, model.getCurrentPlayer().getColor()); // Switched to BLUE
    Assert.assertEquals(7, redPlayer.getPlayerHand().size()); // RED hand decreases
    Assert.assertEquals(8, bluePlayer.getPlayerHand().size()); // BLUE hand is still 8
  }


  @Test(expected = IllegalStateException.class)
  public void testOperationsWhenGameHasNotStarted() {
    GamePlayer redPlayer = model.getPlayers().get(0);
    redPlayer.addCardToHand(deck.get(0));
    model.placeCard(0, 0, redPlayer.getPlayerHand().get(0));

    model.switchTurn();
  }

  @Test(expected = IllegalStateException.class)
  public void testStartGameWithInvalidDeck() {
    deck.clear(); // invalid deck.

    model.startGame(0); // throws exception
  }

  @Test
  public void testStartingGameWithTooFewCards() {
    List<Card> deck = new CardConfigReader()
            .readCards("src" + File.separator + "resources"
                    + File.separator + "TooFewCardDb.txt"); // 15 cards
    Grid grid = new GridConfigReader()
            .readGridFromFile("src" + File.separator + "resources"
                    + File.separator + "GridWithCellConnections.txt"); // 19 card cell
    PlayerFactory playerFactory = new HumanPlayerFactory();
    GameModel model = new ThreeTriosModel(grid, playerFactory, deck);
    IllegalStateException exception = Assert.assertThrows(IllegalStateException.class, () -> {
      model.startGame(0);
    });
    Assert.assertTrue(exception.toString().contains("Not enough cards to start the game."));
  }

  @Test
  public void testStartingGameWithEvenNumberOfCardCells() {
    List<Card> deck = new CardConfigReader()
            .readCards("src" + File.separator + "resources"
                    + File.separator + "CardDb.txt");
    Grid grid = new GridConfigReader()
            .readGridFromFile("src" + File.separator + "resources"
                    + File.separator + "GridWithEvenCellNum.txt");
    PlayerFactory playerFactory = new HumanPlayerFactory();
    GameModel model = new ThreeTriosModel(grid, playerFactory, deck);
    IllegalStateException exception = Assert.assertThrows(IllegalStateException.class, () -> {
      model.startGame(0);
    });
    Assert.assertTrue(exception.toString().contains("Grid must have an odd number of card cells."));
  }

  @Test
  public void testBattlePhase() {
    GamePlayer redPlayer = model.getPlayers().get(0);
    GamePlayer bluePlayer = model.getPlayers().get(1);

    model.startGame(0);
    model.placeCard(0, 0, redPlayer.getPlayerHand().get(1)); // 4 on east
    model.placeCard(0, 1, bluePlayer.getPlayerHand().get(0)); // 8 on west
    Assert.assertEquals(Player.BLUE, grid.getCell(0, 1).getOwner().getColor());
  }

  @Test
  public void testSwitchTurns() {
    GamePlayer redPlayer = model.getPlayers().get(0);
    GamePlayer bluePlayer = model.getPlayers().get(1);

    model.startGame(0);

    Assert.assertEquals(Player.RED, model.getCurrentPlayer().getColor());

    model.placeCard(0, 0, redPlayer.getPlayerHand().get(0));
    Assert.assertEquals(bluePlayer, model.getCurrentPlayer());

    model.placeCard(0, 1, bluePlayer.getPlayerHand().get(1));
    Assert.assertEquals(Player.RED, model.getCurrentPlayer().getColor());
  }

  @Test
  public void testGameEndCondition() {

    model.startGame(0);
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (!grid.getCell(row, col).isHole()) {
          model.placeCard(row, col, model.getCurrentPlayer().getPlayerHand().get(0));
        }
      }
    }
    Assert.assertTrue(model.isGameOver());
  }

  @Test
  public void testBattleSwitchesWhoOwnsCardAndCell() {
    GamePlayer bluePlayer = model.getPlayers().get(1);
    GamePlayer redPlayer = model.getPlayers().get(0);
    model.startGame(0);
    model.placeCard(0, 0, redPlayer.getPlayerHand().get(1)); // 4 on east
    model.placeCard(0, 1, bluePlayer.getPlayerHand().get(0)); // 8 on west
    Assert.assertEquals(bluePlayer, grid.getCell(0, 0).getOwner());
    Assert.assertEquals(bluePlayer, grid.getCell(0, 1).getOwner());
  }

  @Test
  public void testTieBetweenCellsMeansNothingHappens() {
    GamePlayer bluePlayer = model.getPlayers().get(1);
    GamePlayer redPlayer = model.getPlayers().get(0);

    model.startGame(0);

    model.placeCard(0, 0, redPlayer.getPlayerHand().get(0)); // 8 on east
    model.placeCard(0, 1, bluePlayer.getPlayerHand().get(0)); // 8 on west

    Assert.assertEquals(redPlayer, grid.getCell(0, 0).getOwner());
    Assert.assertEquals(bluePlayer, grid.getCell(0, 1).getOwner());
  }

  @Test
  public void testGameTyingLogicWorks() throws IOException {
    model.startGame(0);
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (!grid.getCell(row, col).isHole()) {
          model.placeCard(row, col, model.getCurrentPlayer().getPlayerHand().get(0));
        }
      }
    }
    Assert.assertTrue(model.isGameOver());
    Assert.assertNull(model.getWinner()); // since we said that tie = null.
  }

  private GameModel createModelForEasyTesting() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    List<Card> deck = cardConfigReader.readCards("src" + File.separator + "resources"
            + File.separator + "CardDb.txt");
    Grid grid = gridConfigReader.readGridFromFile("src" + File.separator + "resources"
            + File.separator + "EasyTestingGridDb.txt");

    PlayerFactory playerFactory = new HumanPlayerFactory();
    return new ThreeTriosModel(grid, playerFactory, deck);
  }

  @Test
  public void testWinningLogicWorks() {
    GameModel gameModel = createModelForEasyTesting();
    GamePlayer bluePlayer = gameModel.getPlayers().get(1);
    GamePlayer redPlayer = gameModel.getPlayers().get(0);
    gameModel.startGame(0);
    Assert.assertEquals(bluePlayer.getPlayerHand().size(), 13);
    Assert.assertEquals(redPlayer.getPlayerHand().size(), 13);
    for (int row = 0; row < gameModel.getGrid().getRows(); row++) {
      for (int col = 0; col < gameModel.getGrid().getCols(); col++) {
        if (!gameModel.getGrid().getCell(row, col).isHole()) {
          gameModel.placeCard(row, col,
                  gameModel.getCurrentPlayer().getPlayerHand().get(0));
        }
      }
    }

    Assert.assertTrue(gameModel.isGameOver());
    Assert.assertEquals(bluePlayer, gameModel.getWinner());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardInOccupiedCell() {
    GamePlayer bluePlayer = model.getPlayers().get(1);
    GamePlayer redPlayer = model.getPlayers().get(0);

    model.startGame(0);
    Card card1 = redPlayer.getPlayerHand().get(0);
    Card card2 = bluePlayer.getPlayerHand().get(1);

    model.placeCard(0, 0, card1);
    model.placeCard(0, 0, card2);
  }
}
