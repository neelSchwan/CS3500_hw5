package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GamePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy that prioritizes placing cards in corner cells.
 * If no corner is available, it falls back.
 */
public class CornerStrategy implements ThreeTriosStrategy {
  private final ThreeTriosStrategy fallbackStrategy;

  /**
   * Empty constructor if there is no fallback strat provided.
   */
  public CornerStrategy() {
    this.fallbackStrategy = new FlipMostStrategy();
  }

  /**
   * Constructor that takes in a strategy to fall back to in case this doesn't work.
   *
   * @param fallbackStrategy strat to fall back to.
   */
  public CornerStrategy(ThreeTriosStrategy fallbackStrategy) {
    this.fallbackStrategy = fallbackStrategy;
  }

  @Override
  public Move chooseMove(GameModel model) {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    if (model.isGameOver()) {
      throw new IllegalStateException("Game has not started yet.");
    }

    int rows = model.getGrid().getRows();
    int cols = model.getGrid().getCols();

    // Define corners
    List<int[]> corners = new ArrayList<>();
    corners.add(new int[]{0, 0});
    corners.add(new int[]{0, cols - 1});
    corners.add(new int[]{rows - 1, 0});
    corners.add(new int[]{rows - 1, cols - 1});

    // Try each corner if valid
    for (int[] corner : corners) {
      int cornerRow = corner[0];
      int cornerCol = corner[1];
      if (model.isValidMove(cornerRow, cornerCol)) {

        Move bestCardMove = null;
        int bestCardScore = -1;

        for (Card card : currentPlayer.getPlayerHand()) {
          int measure = measureCornerCard(card, cornerRow, cornerCol, rows, cols);
          if (measure > bestCardScore) {
            bestCardScore = measure;
            bestCardMove = new Move(cornerRow, cornerCol, card, measure);
          } else if (measure == bestCardScore && bestCardMove != null) {
            Move currentMove = new Move(cornerRow, cornerCol, card, measure);
            if (currentMove.isBetterThan(bestCardMove)) {
              bestCardMove = currentMove;
            }
          }
        }

        if (bestCardMove != null) {
          return bestCardMove;
        }
      }
    }

    return fallbackStrategy.chooseMove(model);
  }

  /**
   * Measures how stable a card is if placed in a corner cell by summing
   * the relevant edges for that corner.
   */
  private int measureCornerCard(Card card, int row, int col, int totalRows, int totalCols) {
    int measure = 0;
    if (row == 0 && col == 0) { // top-left corner
      measure = card.getAttackValue(Direction.EAST) + card.getAttackValue(Direction.SOUTH);
    } else if (row == 0 && col == totalCols - 1) { // top-right corner
      measure = card.getAttackValue(Direction.WEST) + card.getAttackValue(Direction.SOUTH);
    } else if (row == totalRows - 1 && col == 0) { // bottom-left corner
      measure = card.getAttackValue(Direction.EAST) + card.getAttackValue(Direction.NORTH);
    } else if (row == totalRows - 1 && col == totalCols - 1) { // bottom-right corner
      measure = card.getAttackValue(Direction.WEST) + card.getAttackValue(Direction.NORTH);
    }
    return measure;
  }
}
