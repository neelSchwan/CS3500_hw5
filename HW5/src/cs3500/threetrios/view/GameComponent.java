package cs3500.threetrios.view;

import java.awt.Graphics2D;

/**
 * Class for creating a component that will go into the view, such as a grid or hand panel.
 * Includes methods to render and refresh a component based on the game state.
 */
public interface GameComponent {

  /**
   * Renders the component using the specified Graphics2D object.
   * This method should be called by the component's paint or paintComponent method.
   *
   * @param g2d the graphics object to use for rendering
   */
  void render(Graphics2D g2d);

  /**
   * Refreshes the display of the component based on the latest game state.
   */
  void refresh();
}
