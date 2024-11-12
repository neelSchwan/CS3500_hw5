package cs3500.threetrios.view;

import javax.swing.JPanel;

public class ThreeTriosGamePanel extends JPanel implements ThreeTriosPanel {

  /**
   * Draws the Three-Trios Grid based on the current game state.
   */
  @Override
  public void drawGrid() {
    
  }

  /**
   * Updates the grid given a specific row and col.
   *
   * @param row 0-indexed row value to place a card at.
   * @param col 0-indexed col value to place a card at.
   */
  @Override
  public void updateMove(int row, int col) {

  }

  /**
   * Clears the board so that a new game can be started.
   * Will be useful if the player wants to reset the game state (?).
   */
  @Override
  public void clearBoard() {

  }

  /**
   * Based on where the player clicks the mouse, the card they selected will be highlighted.
   * If there is no card where they click, then nothing happens.
   *
   * @param x x-coordinate where the mouse was clicked.
   * @param y y-coordinate where the mouse was clicked.
   */
  @Override
  public void highlightSelectedCard(int x, int y) {

  }
}
