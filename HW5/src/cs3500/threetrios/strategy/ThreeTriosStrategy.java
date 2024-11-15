package cs3500.threetrios.strategy;

import cs3500.threetrios.model.GameModel;

/**
 * A strategy interface for selecting a move in the ThreeTrios game.
 */
public interface ThreeTriosStrategy {
  /**
   * Choose the best move for the current player based on some chosen strategy.
   *
   * @param model the current state of the ThreeTrios game.
   * @return the chosen move.
   * @throws IllegalStateException if no valid move can be found.
   */
  Move chooseMove(GameModel model);
}