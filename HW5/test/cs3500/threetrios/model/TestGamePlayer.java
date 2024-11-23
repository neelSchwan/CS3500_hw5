package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import cs3500.threetrios.strategy.FlipMostStrategy;
import cs3500.threetrios.strategy.ThreeTriosStrategy;

import static cs3500.threetrios.model.Player.BLUE;
import static cs3500.threetrios.model.Player.RED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

/**
 * Class for testing the player implementation (HUMAN AND AI).
 */
public class TestGamePlayer {

  private GamePlayer humanPlayer;
  private GamePlayer aiPlayer;
  private ThreeTriosStrategy strategy;
  private GameModel model;

  /**
   * Method for setting up each instance of a player before each test.
   */
  @Before
  public void setUp() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    List<Card> deck = cardConfigReader.readCards("HW5" + File.separator + "src" + File.separator
            + "resources" + File.separator + "CardDb.txt");
    Grid grid = gridConfigReader.readGridFromFile("HW5" + File.separator + "src" + File.separator
            + "resources" + File.separator + "EasyTestingGridDb.txt");

    strategy = new FlipMostStrategy();
    model = new ThreeTriosModel(grid, deck);

    humanPlayer = new HumanPlayer(RED, new ArrayList<>());
    aiPlayer = new AIPlayer(BLUE, new ArrayList<>(), strategy, model);

    model.addPlayer(humanPlayer);
    model.addPlayer(aiPlayer);
  }

  @Test
  public void testChooseCard() {
    model.startGame(0);
    Card testHumanCard = humanPlayer.chooseCard(0);
    Card expectedHumanCard = new GameCard("darius", 7, 9, 8, 4);
    assertEquals(testHumanCard, expectedHumanCard);

    Card testAiCard = aiPlayer.chooseCard(0);
    Card expectedAiCard = new GameCard("vi", 7, 6, 10, 8);
    assertEquals(testAiCard, expectedAiCard);
  }

  @Test
  public void testChoosePosition() {
    model.startGame(0);
    int[] testHuman = humanPlayer.choosePosition(0, 0);
    assertArrayEquals(testHuman, new int[]{0, 0});

    int[] testAi = aiPlayer.choosePosition(0, 1);
    assertArrayEquals(testAi, new int[]{0, 1});
  }

  @Test
  public void testGetColor() {
    model.startGame(0);
    assertEquals(humanPlayer.getColor(), RED);
    assertEquals(aiPlayer.getColor(), BLUE);
  }

  @Test
  public void testGetPlayerHand() {
    model.startGame(0);
    Card humanCard1 = new GameCard("darius", 7, 9, 8, 4);
    Card humanCard2 = new GameCard("ekko", 8, 9, 4, 6);
    Card humanCard3 = new GameCard("katarina", 10, 7, 6, 5);
    Card humanCard4 = new GameCard("vayne", 9, 3, 10, 6);
    Card humanCard5 = new GameCard("warwick", 9, 7, 6, 5);

    Card aiCard1 = new GameCard("vi", 7, 6, 10, 8);
    Card aiCard2 = new GameCard("riven", 8, 6, 10, 7);
    Card aiCard3 = new GameCard("heimerdinger", 8, 7, 6, 4);
    Card aiCard4 = new GameCard("twitch", 10, 10, 10, 10);
    Card aiCard5 = new GameCard("taliyah", 7, 10, 6, 9);

    List<Card> humanHand = List.of(humanCard1, humanCard2, humanCard3, humanCard4, humanCard5);
    List<Card> aiHand = List.of(aiCard1, aiCard2, aiCard3, aiCard4, aiCard5);

    assertArrayEquals(humanHand.toArray(), humanPlayer.getPlayerHand().toArray());
    assertArrayEquals(aiHand.toArray(), aiPlayer.getPlayerHand().toArray());
  }

  @Test
  public void testSetColor() {
    model.startGame(0);
    GamePlayer testHumanPlayer = new HumanPlayer(RED, new ArrayList<>());
    assertEquals(RED, testHumanPlayer.getColor());
    testHumanPlayer.setColor(BLUE);

    assertEquals(BLUE, testHumanPlayer.getColor());

    GamePlayer testAiPlayer = new AIPlayer(BLUE, new ArrayList<>(), strategy, model);
    assertEquals(BLUE, testAiPlayer.getColor());
    Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
      testAiPlayer.setColor(RED);
    });

    assertEquals("AIPlayer color cannot be changed.", exception.getMessage());
  }

  @Test
  public void testAddCardToHand() {
    model.startGame(0);
    Card forHumanCard = new GameCard("dummy", 1, 1, 1, 1);
    Card forAiCard = new GameCard("dummy", 2, 2, 2, 2);

    Card humanCard1 = new GameCard("darius", 7, 9, 8, 4);
    Card humanCard2 = new GameCard("ekko", 8, 9, 4, 6);
    Card humanCard3 = new GameCard("katarina", 10, 7, 6, 5);
    Card humanCard4 = new GameCard("vayne", 9, 3, 10, 6);
    Card humanCard5 = new GameCard("warwick", 9, 7, 6, 5);

    Card aiCard1 = new GameCard("vi", 7, 6, 10, 8);
    Card aiCard2 = new GameCard("riven", 8, 6, 10, 7);
    Card aiCard3 = new GameCard("heimerdinger", 8, 7, 6, 4);
    Card aiCard4 = new GameCard("twitch", 10, 10, 10, 10);
    Card aiCard5 = new GameCard("taliyah", 7, 10, 6, 9);

    List<Card> humanHand = new ArrayList<>(
            List.of(humanCard1, humanCard2, humanCard3, humanCard4, humanCard5));
    List<Card> aiHand = new ArrayList<>(List.of(aiCard1, aiCard2, aiCard3, aiCard4, aiCard5));

    assertArrayEquals(humanHand.toArray(), humanPlayer.getPlayerHand().toArray());
    assertArrayEquals(aiHand.toArray(), aiPlayer.getPlayerHand().toArray());

    humanPlayer.addCardToHand(forHumanCard);
    humanHand.add(forHumanCard);
    aiPlayer.addCardToHand(forAiCard);
    aiHand.add(forAiCard);

    assertArrayEquals(humanHand.toArray(), humanPlayer.getPlayerHand().toArray());
    assertArrayEquals(aiHand.toArray(), aiPlayer.getPlayerHand().toArray());
  }

  @Test
  public void testRemoveCardFromHand() {
    model.startGame(0);
    Card humanCard1 = new GameCard("darius", 7, 9, 8, 4);
    Card humanCard2 = new GameCard("ekko", 8, 9, 4, 6);
    Card humanCard3 = new GameCard("katarina", 10, 7, 6, 5);
    Card humanCard4 = new GameCard("vayne", 9, 3, 10, 6);
    Card humanCard5 = new GameCard("warwick", 9, 7, 6, 5);

    Card aiCard1 = new GameCard("vi", 7, 6, 10, 8);
    Card aiCard2 = new GameCard("riven", 8, 6, 10, 7);
    Card aiCard3 = new GameCard("heimerdinger", 8, 7, 6, 4);
    Card aiCard4 = new GameCard("twitch", 10, 10, 10, 10);
    Card aiCard5 = new GameCard("taliyah", 7, 10, 6, 9);

    List<Card> humanHand = new ArrayList<>(
            List.of(humanCard1, humanCard2, humanCard3, humanCard4, humanCard5));
    List<Card> aiHand = new ArrayList<>(List.of(aiCard1, aiCard2, aiCard3, aiCard4, aiCard5));

    assertArrayEquals(humanHand.toArray(), humanPlayer.getPlayerHand().toArray());
    assertArrayEquals(aiHand.toArray(), aiPlayer.getPlayerHand().toArray());

    humanPlayer.removeCardFromHand(humanCard5);
    humanHand.remove(humanCard5);
    aiPlayer.removeCardFromHand(aiCard5);
    aiHand.remove(aiCard5);

    assertArrayEquals(humanHand.toArray(), humanPlayer.getPlayerHand().toArray());
    assertArrayEquals(aiHand.toArray(), aiPlayer.getPlayerHand().toArray());
  }
}
