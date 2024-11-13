package cs3500.threetrios.model;

import java.util.List;

public class MaxFlipsStrategy implements Strategy {

  @Override
  public Move determineMove(GameModel gameState, List<Card> hand) {
    int bestRow = -1;
    int bestCol = -1;
    Card bestCard = null;
    int maxFlips = -1;

    // Loop through each cell in the grid
    for (int i = 0; i < gameState.getGrid().getRows(); i++) {
      for (int j = 0; j < gameState.getGrid().getCols(); j++) {

        if (gameState.isValidMove(i, j)) {

          for (Card card : hand) {
            int flipCount = gameState.maxCombo(card, i, j);

            if (flipCount > maxFlips
                    || (flipCount == maxFlips && isUpperLeftMost(i, j, bestRow, bestCol))) {
              bestRow = i;
              bestCol = j;
              bestCard = card;
              maxFlips = flipCount;
            }
          }
        }
      }
    }

    if (bestCard == null && !hand.isEmpty()) {
      int[] openPosition = findUpperLeftOpenPosition(gameState);
      bestRow = openPosition[0];
      bestCol = openPosition[1];
      bestCard = hand.get(0);
    }

    return new Move(bestRow, bestCol, bestCard);
  }

  private boolean isUpperLeftMost(int row, int col, int bestRow, int bestCol) {
    return bestRow == -1 || row < bestRow || (row == bestRow && col < bestCol);
  }

  private int[] findUpperLeftOpenPosition(GameModel gameState) {
    for (int i = 0; i < gameState.getGrid().getRows(); i++) {
      for (int j = 0; j < gameState.getGrid().getCols(); j++) {
        if (gameState.isValidMove(i, j)) {
          return new int[]{i, j};
        }
      }
    }
    return new int[]{-1, -1}; // reutrn some invalid position
  }
}
