package cs3500.threetrios.strategy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.GameCard;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.HumanPlayer;
import cs3500.threetrios.model.MockThreeTriosGameModel;
import cs3500.threetrios.model.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAllStrategies {
  private StringBuilder log;
  private GamePlayer mockPlayer;
  private Grid testGrid;
  private MockThreeTriosGameModel mockModel;

  @Before
  public void setup() {
    log = new StringBuilder();

    List<Card> playerHand = new ArrayList<>();
    playerHand.add(new GameCard("StrongCard", 9, 9, 9, 9));
    playerHand.add(new GameCard("WeakCard", 1, 1, 1, 1));

    mockPlayer = new HumanPlayer(Player.RED, playerHand);
    testGrid = new Grid(3, 3);
    mockModel = new MockThreeTriosGameModel(log, mockPlayer, testGrid, 2);
  }

  @Test
  public void testCornerStrategyFindsAllCorners() {
    CornerStrategy strategy = new CornerStrategy();
    Move move = strategy.chooseMove(mockModel);

    String logContent = log.toString();
    System.out.println(logContent);
    assertTrue(logContent.contains("Checking move validity at (0,0)"));
    assertTrue(logContent.contains("Checking move validity at (0,2)"));
    assertTrue(logContent.contains("Checking move validity at (2,0)"));
    assertTrue(logContent.contains("Checking move validity at (2,2)"));
  }

  @Test
  public void testCornerStrategyChoosesStrongestCard() {
    CornerStrategy strategy = new CornerStrategy();
    Move move = strategy.chooseMove(mockModel);

    assertEquals("Should choose StrongCard", "StrongCard", move.getCard().getName());
    assertTrue("Should choose a corner position",
            (move.getRow() == 0 || move.getRow() == 2)
                    && (move.getCol() == 0 || move.getCol() == 2));
  }

  @Test
  public void testCornerStrategyFallsBackToFlipMost() {
    Grid invalidCornersGrid = new Grid(3, 3);
    MockThreeTriosGameModel mockModelInvalidCorners = new MockThreeTriosGameModel(log, mockPlayer,
            invalidCornersGrid, 5) {
      @Override
      public boolean isValidMove(int row, int col) {
        return row == 1 && col == 1;
      }
    };

    CornerStrategy strategy = new CornerStrategy();
    Move move = strategy.chooseMove(mockModelInvalidCorners);

    assertEquals("Should place in center when corners invalid", 1, move.getRow());
    assertEquals("Should place in center when corners invalid", 1, move.getCol());
  }

  @Test
  public void testFlipMostStrategyChecksAllCells() {
    FlipMostStrategy strategy = new FlipMostStrategy();
    Move move = strategy.chooseMove(mockModel);

    String logContent = log.toString();
    System.out.println(logContent);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertTrue(logContent.contains(
                String.format("Checking move validity at (%d,%d)", i, j)));
      }
    }
  }

  @Test
  public void testFlipMostStrategyChoosesHighestCombo() {
    MockThreeTriosGameModel mockModelWithCombos = new MockThreeTriosGameModel(log, mockPlayer,
            testGrid, 0) {
      @Override
      public int maxCombo(Card card, int row, int col) {
        if (row == 1 && col == 1) {
          return 3;
        }
        return 1;
      }
    };

    FlipMostStrategy strategy = new FlipMostStrategy();
    Move move = strategy.chooseMove(mockModelWithCombos);

    assertEquals("Should choose position with highest combo", 1, move.getRow());
    assertEquals("Should choose position with highest combo", 1, move.getCol());
  }

  @Test(expected = IllegalStateException.class)
  public void testStrategiesFailOnGameOver() {
    MockThreeTriosGameModel gameOverModel = new MockThreeTriosGameModel(log, mockPlayer,
            testGrid, 0) {
      @Override
      public boolean isGameOver() {
        return true;
      }
    };

    new FlipMostStrategy().chooseMove(gameOverModel);
  }

  @Test
  public void testTieBreakingByPosition() {
    MockThreeTriosGameModel mockModelSameCombos = new MockThreeTriosGameModel(log, mockPlayer,
            testGrid, 1) {
      @Override
      public boolean isValidMove(int row, int col) {
        return true;
      }

      @Override
      public int maxCombo(Card card, int row, int col) {
        return 1;
      }
    };

    FlipMostStrategy strategy = new FlipMostStrategy();
    Move move = strategy.chooseMove(mockModelSameCombos);

    assertEquals("Should choose uppermost position", 0, move.getRow());
    assertEquals("Should choose leftmost position", 0, move.getCol());
  }
}