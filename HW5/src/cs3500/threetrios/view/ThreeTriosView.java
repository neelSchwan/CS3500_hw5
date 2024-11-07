package cs3500.threetrios.view;

import java.io.IOException;


/**
 * Interface for the view of three-trios game.
 */
public interface ThreeTriosView {
  /**
   * Generates a textual / string representation of the current game state.
   */
  void render() throws IOException;

  /**
   * Outputs the current game state to the console.
   */
  void display() throws IOException;


}
