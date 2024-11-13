package cs3500.threetrios.view;

import javax.swing.JPanel;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.Direction;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * GUI panel for the Three-Trios game, handling the grid and player hands.
 */
public class ThreeTriosGamePanel extends JPanel implements ThreeTriosPanel {
  ThreeTriosController features;
  private final ReadonlyThreeTriosModel model;
  private static final int HAND_GAP = 5;
  private static final double HAND_WIDTH_PROPORTION = 1.5;

  public ThreeTriosGamePanel(ReadonlyThreeTriosModel model) {
    this.model = model;
  }

  public void addClickListener(ThreeTriosController features) {
    this.features = features;
    this.addMouseListener(new TTGClickListener());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawGrid(g);
    drawPlayersHands(g);
  }

  /**
   * Draws the game grid, centered with a 10px gap between the grid and each player's hand.
   */
  public void drawGrid(Graphics g) {
    int totalRows = model.getGrid().getRows();
    int totalCols = model.getGrid().getCols();

    int cellHeight = getHeight() / totalRows;
    int cardWidth = (int) (cellHeight / HAND_WIDTH_PROPORTION);

    int availableWidth = getWidth() - 2 * cardWidth;
    int cellWidth = availableWidth / totalCols;

    int gridStartX = cardWidth + 5;

    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalCols; col++) {
        Cell cell = model.getGrid().getCell(row, col);
        setCellColor(g, cell.getCellType());

        int xPos = gridStartX + col * cellWidth;
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
   * Draws both players' hands on either side of the game panel with a fixed 10px gap from the grid.
   */
  private void drawPlayersHands(Graphics g) {
    int totalRows = model.getGrid().getRows();
    int cellHeight = getHeight() / totalRows;
    int cardWidth = (int) (cellHeight / HAND_WIDTH_PROPORTION);

    drawHand(g, model.getPlayers().get(0).getPlayerHand(), 0, cellHeight, cardWidth,
            new Color(194, 95, 95, 255));

    int blueXPos = getWidth() - cardWidth;
    drawHand(g, model.getPlayers().get(1).getPlayerHand(), blueXPos, cellHeight, cardWidth,
            new Color(115, 148, 234));
  }

  /**
   * Draws a player's hand aligned with cell heights, at a fixed x-position.
   *
   * @param g          Graphics object for drawing.
   * @param hand       List of cards in the player's hand.
   * @param xStart     Fixed x-position for the hand.
   * @param cardHeight Height of each card (matching cell height).
   * @param cardWidth  Width of each card.
   * @param color      Color for the hand's background.
   */
  private void drawHand(Graphics g, List<Card> hand, int xStart, int cardHeight, int cardWidth, Color color) {
    for (int i = 0; i < hand.size(); i++) {
      int yPos = i * cardHeight; // Align cards with grid cell rows

      g.setColor(color);
      g.fillRect(xStart, yPos, cardWidth, cardHeight);

      g.setColor(Color.BLACK);
      g.drawRect(xStart, yPos, cardWidth, cardHeight);

      Card card = hand.get(i);
      int northAttack = card.getAttackValue(Direction.NORTH);
      int eastAttack = card.getAttackValue(Direction.EAST);
      int westAttack = card.getAttackValue(Direction.WEST);
      int southAttack = card.getAttackValue(Direction.SOUTH);

      g.drawString(formatAttackValue(northAttack), xStart + cardWidth / 2, yPos + 15);
      g.drawString(formatAttackValue(eastAttack), xStart + cardWidth - 15, yPos + cardHeight / 2);
      g.drawString(formatAttackValue(westAttack), xStart + 5, yPos + cardHeight / 2);
      g.drawString(formatAttackValue(southAttack), xStart + cardWidth / 2,
              yPos + cardHeight - 5);
    }
  }

  /**
   * Formats the attack value.
   *
   * @param attackValue the attack value to display
   * @return the formatted attack value as a string
   */
  private String formatAttackValue(int attackValue) {
    return attackValue == 10 ? "A" : String.valueOf(attackValue);
  }

  /**
   * Updates the grid and repaints the panel when a move is made.
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

  private class TTGClickListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
  }
}
