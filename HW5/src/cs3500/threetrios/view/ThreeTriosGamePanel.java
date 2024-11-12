package cs3500.threetrios.view;

import javax.swing.JPanel;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.CellType;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.util.List;

/**
 * GUI panel for the Three-Trios game, handling the grid and player hands.
 */
public class ThreeTriosGamePanel extends JPanel implements ThreeTriosPanel {
  private final ReadonlyThreeTriosModel model;
  private static final int HAND_WIDTH = 50;
  private static final int CARD_WIDTH = 30;
  private static final int CARD_HEIGHT = 60;

  public ThreeTriosGamePanel(ReadonlyThreeTriosModel model) {
    this.model = model;
    setPreferredSize(new Dimension(400, 400));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawGrid(g);
    drawPlayersHands(g);
  }

  /**
   * Draws the game grid based on the model's state.
   */
  public void drawGrid(Graphics g) {
    int totalRows = model.getGrid().getRows();
    int totalCols = model.getGrid().getCols();
    int cellWidth = getWidth() / totalCols;
    int cellHeight = getHeight() / totalRows;

    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalCols; col++) {
        Cell cell = model.getGrid().getCell(row, col);
        setCellColor(g, cell.getCellType());

        int xPos = col * cellWidth;
        int yPos = row * cellHeight;

        g.fillRect(xPos, yPos, cellWidth, cellHeight);
        g.setColor(Color.BLACK);
        g.drawRect(xPos, yPos, cellWidth, cellHeight);
      }
    }
  }

  /**
   * Sets the color for each cell based on its type.
   */
  private void setCellColor(Graphics g, CellType cellType) {
    switch (cellType) {
      case CARD_CELL:
        g.setColor(Color.YELLOW);
        break;
      case HOLE_CELL:
        g.setColor(Color.LIGHT_GRAY);
        break;
      default:
        g.setColor(Color.WHITE);
    }
  }

  /**
   * Draws both players' hands on either side of the game panel.
   */
  private void drawPlayersHands(Graphics g) {
    drawHand(g, model.getPlayers().get(0).getPlayerHand(), 10, Color.RED);
    drawHand(g, model.getPlayers().get(1).getPlayerHand(), getWidth() - HAND_WIDTH - 10, Color.BLUE);
  }

  /**
   * Draws a player's hand.
   *
   * @param g      Graphics object for drawing.
   * @param hand   List of cards in the player's hand.
   * @param xStart Starting x-position for the hand.
   * @param color  Color for the hand's background.
   */
  private void drawHand(Graphics g, List<Card> hand, int xStart, Color color) {
    for (int i = 0; i < hand.size(); i++) {
      int yPos = i * (CARD_HEIGHT + 10);

      g.setColor(color);
      g.fillRect(xStart, yPos, CARD_WIDTH, CARD_HEIGHT);

      g.setColor(Color.BLACK);
      g.drawRect(xStart, yPos, CARD_WIDTH, CARD_HEIGHT);
      g.drawString(hand.get(i).getName(), xStart + 5, yPos + CARD_HEIGHT / 2);
    }
  }

  /**
   * Updates the grid and repaints the panel when a move is made.
   *
   * @param row Row to update.
   * @param col Column to update.
   */
  @Override
  public void updateMove(int row, int col) {
    repaint();
  }

  /**
   * Clears the board to reset the game state.
   */
  @Override
  public void clearBoard() {
    repaint();
  }

  /**
   * Highlights a selected card if clicked.
   *
   * @param x x-coordinate of click.
   * @param y y-coordinate of click.
   */
  @Override
  public void highlightSelectedCard(int x, int y) {
    // Implement highlighting logic if needed
  }
}
