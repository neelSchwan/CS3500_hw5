package cs3500.threetrios.view;

import java.io.IOException;

import cs3500.threetrios.model.GameModel;

/**
 * Interface for the view of three-trios game.
 */
public interface ThreeTriosView {
  /**
   * Generates a textual / string representation of the current game state.
   *
   * @param model three-trios model that is used for getting the current game state.
   */
  void render(GameModel model) throws IOException;

  /**
   * Outputs the current game state to the console.
   *
   * @param model three-trios model that is used for getting the current game state.
   */
  void display(GameModel model) throws IOException;
}
