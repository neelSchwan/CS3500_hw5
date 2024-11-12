package cs3500.threetrios.view;

import java.awt.*;

import javax.swing.*;

/**
 * Interface for the three-trios-panel.
 * This will handle drawing all GUI components like the cards and grid.
 */
public interface ThreeTriosPanel {

  /**
   * Draws the Three-Trios Grid based on the current game state.
   *
   * @param g The Graphics object for drawing.
   */
  void drawGrid(Graphics g);

  /**
   * Updates the grid given a specific row and col.
   *
   * @param row 0-indexed row value to place a card at.
   * @param col 0-indexed col value to place a card at.
   */
  void updateMove(int row, int col);


  /**
   * Clears the board so that a new game can be started.
   * Will be useful if the player wants to reset the game state (?).
   */
  void clearBoard();

  /**
   * Based on where the player clicks the mouse, the card they selected will be highlighted.
   * If there is no card where they click, then nothing happens.
   *
   * @param x x-coordinate where the mouse was clicked.
   * @param y y-coordinate where the mouse was clicked.
   */
  void highlightSelectedCard(int x, int y);
}
