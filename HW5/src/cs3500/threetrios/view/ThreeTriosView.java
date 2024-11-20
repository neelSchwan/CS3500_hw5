package cs3500.threetrios.view;

import java.io.IOException;



/**
 * The ThreeTriosView interface defines methods for rendering and displaying
 * the state of the game.
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
