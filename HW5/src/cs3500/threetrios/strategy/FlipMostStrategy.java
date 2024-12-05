package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * Strategy that selects the card and position that will flip the most opponent cards.
 */
public class FlipMostStrategy implements ThreeTriosStrategy {
  /**
   * Choose the best move for the current player based on some chosen strategy.
   *
   * @param model the current state of the ThreeTrios game.
   * @return the chosen move.
   * @throws IllegalStateException if no valid move can be found.
   */
  @Override
  public Move chooseMove(ReadonlyThreeTriosModel model) {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    if (model.isGameOver()) {
      throw new IllegalStateException("Game has not started yet.");
    }

    int maxFlips = -1;
    Move bestMove = null;

    for (int row = 0; row < model.getGrid().getRows(); row++) {
      for (int col = 0; col < model.getGrid().getCols(); col++) {
        if (model.isValidMove(row, col)) {
          for (Card card : currentPlayer.getPlayerHand()) {
            int flipCount = model.maxCombo(card, row, col);
            if (flipCount > maxFlips) {
              maxFlips = flipCount;
              bestMove = new Move(row, col, card, flipCount);
            } else if (flipCount == maxFlips) {
              Move currentMove = new Move(row, col, card, flipCount);
              if (currentMove.isBetterThan(bestMove)) {
                bestMove = currentMove;
              }
            }
          }
        }
      }
    }
    if (bestMove == null) {
      throw new IllegalStateException("No valid moves available for FlipMostStrategy.");
    }

    return bestMove;
  }
}
