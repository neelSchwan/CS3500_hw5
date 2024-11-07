package cs3500.threetrios.view;

import cs3500.threetrios.controller.ThreeTriosController;

public interface ThreeTriosGUIView {
  /**
   * Set up the controller to handle click events in this view.
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
}
