package cs3500.threetrios.view;

import java.io.IOException;

import javax.swing.JFrame;

import cs3500.threetrios.controller.ThreeTriosController;

public class ThreeTriosGameGUIView extends JFrame implements ThreeTriosView {

  //private final ThreeTriosPanel panel;

  /**
   * Constructs a TicTacToeView by initializing a window with a panel for the game board.
   * The window size is set to 600x600 pixels, and it is centered on the screen.
   */
  public ThreeTriosGameGUIView() {
//    this.panel = new ThreeTriosGamePanel();
    this.setSize(600, 400);
//    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//    this.add(this.panel);
  }

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  @Override
  public void addClickListener(ThreeTriosController listener) {

  }

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  @Override
  public void refresh() {

  }

  /**
   * Make the view visible to start the game session.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void render() throws IOException {

  }

  @Override
  public void display() throws IOException {

  }
}
