package cs3500.threetrios.controller;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import cs3500.threetrios.model.GameCard;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.HumanPlayer;
import cs3500.threetrios.model.MockThreeTriosGameModel;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.MockGameView;

import static org.junit.Assert.assertTrue;

/**
 * Class for testing the controller for the three-trios game.
 * This class is tested through simulating a game using a mock model and a mock view.
 */
public class TestThreeTriosController {
  private StringBuilder modelLog;
  private StringBuilder viewLog;
  private GameModel mockModel;
  private GameView mockView;
  private GamePlayer redPlayer;
  private GamePlayer bluePlayer;
  private GameController controller;

  /**
   * Sets up each variable, such as the view and model logs, and the controller object before each
   * test.
   */
  @Before
  public void setUp() {
    modelLog = new StringBuilder();
    viewLog = new StringBuilder();

    redPlayer = new HumanPlayer(Player.RED, new ArrayList<>());
    bluePlayer = new HumanPlayer(Player.BLUE, new ArrayList<>());
    Grid grid = new Grid(3, 3);

    mockModel = new MockThreeTriosGameModel(modelLog, redPlayer, grid, 2);
    mockView = new MockGameView(viewLog);

    mockModel.addPlayer(redPlayer);
    mockModel.addPlayer(bluePlayer);

    controller = new ThreeTriosController(mockModel, mockView, redPlayer);
  }

  @Test
  public void testConstructor() {
    assertTrue("Model should add Listener",
            modelLog.toString().contains("Adding game model listener"));
    assertTrue("View should add Listener",
            viewLog.toString().contains("Game event listener added"));
    assertTrue("View should be made visible",
            viewLog.toString().contains("View made visible"));
    assertTrue("View should be updated",
            viewLog.toString().contains("View updated"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullModel() {
    new ThreeTriosController(null, mockView, redPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullView() {
    new ThreeTriosController(mockModel, null, redPlayer);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructorNullPlayer() {
    new ThreeTriosController(mockModel, mockView, null);
  }

  @Test
  public void testUpdateView() {
    viewLog.setLength(0);
    controller.updateView();
    System.out.println(viewLog.toString());
    assertTrue(viewLog.toString().contains("View updated"));
    assertTrue(viewLog.toString().contains("Message displayed: Waiting for the other player..."));
  }

  @Test
  public void testEndGame() {
    viewLog.setLength(0);
    modelLog.setLength(0);
    controller.endGame();
    assertTrue(modelLog.toString().contains("Checking winner"));
  }

  @Test
  public void testHandleCardSelection() {
    viewLog.setLength(0);
    modelLog.setLength(0);
    controller.handleCardSelection(0, redPlayer);
    assertTrue(modelLog.toString().contains("Getting current player"));
    assertTrue(viewLog.toString().contains("Message displayed: It's not your turn."));
  }

  @Test
  public void testOnTurnChanged() {
    viewLog.setLength(0);
    modelLog.setLength(0);
    controller.onTurnChanged(redPlayer);
    assertTrue(viewLog.toString().contains("View enabled set to: true\n"
            + "Active player updated to: RED\n"
            + "View updated\n"
            + "Message displayed: It's your turn, RED player."));
  }

  @Test
  public void testValidCardSelection() {
    viewLog.setLength(0);
    modelLog.setLength(0);
    MockGameView mockView = new MockGameView(viewLog);

    redPlayer.addCardToHand(new GameCard("TestCard", 5, 5, 5, 5));

    ThreeTriosController controller = new ThreeTriosController(mockModel, mockView, redPlayer);
    controller.onTurnChanged(redPlayer);
    mockView.simulateCardSelection(0, redPlayer);
    assertTrue(viewLog.toString().contains("Simulating card selection: index=0, player=RED"));
    assertTrue(viewLog.toString().contains("Message displayed: Selected card: TestCard"));
  }

  @Test
  public void testInvalidCardSelection() {
    viewLog.setLength(0);
    modelLog.setLength(0);
    MockGameView mockView = new MockGameView(viewLog);

    redPlayer.addCardToHand(new GameCard("TestCard", 5, 5, 5, 5));

    ThreeTriosController controller = new ThreeTriosController(mockModel, mockView, redPlayer);
    controller.onTurnChanged(redPlayer);
    mockView.simulateCardSelection(0, bluePlayer);
    assertTrue(viewLog.toString()
            .contains("Message displayed: You cannot select cards from the opponent's hand."));
  }
}
