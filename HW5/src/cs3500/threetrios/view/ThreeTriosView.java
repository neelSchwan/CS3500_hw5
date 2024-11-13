package cs3500.threetrios.view;

import java.io.IOException;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.ThreeTriosGUIController;


/**
 * Interface for the view of three-trios game.
 */
public interface ThreeTriosView {

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(ThreeTriosController listener);

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Generates a textual / string representation of the current game state.
   */
  void render() throws IOException;

  /**
   * Outputs the current game state to the console.
   */
  void display() throws IOException;

}
