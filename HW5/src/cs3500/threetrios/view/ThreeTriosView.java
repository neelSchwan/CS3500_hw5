package cs3500.threetrios.view;

import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Interface for the view of three-trios game.
 */
public interface ThreeTriosView {
  /**
   * Generates a textual / string representation of the current game state
   *
   * @param model three-trios model that is used for getting the current game state.
   * @return String representation of the current game state.
   */
  String render(ThreeTriosModel model);

  /**
   * Outputs the current game state to the console.
   *
   * @param model three-trios model that is used for getting the current game state.
   */
  void display(ThreeTriosModel model);
}
