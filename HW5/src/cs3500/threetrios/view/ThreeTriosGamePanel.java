package cs3500.threetrios.view;

import javax.swing.JPanel;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.view.ReadonlyThreeTriosModel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.List;

/**
 * GUI panel for the Three-Trios game, handling the grid and player hands.
 */
public class ThreeTriosGamePanel extends JPanel implements ThreeTriosPanel {
  private ThreeTriosController features;
  private final ReadonlyThreeTriosModel model;

  public ThreeTriosGamePanel(ReadonlyThreeTriosModel model) {
    this.model = model;
    this.setBackground(Color.WHITE);
  }

  public void addClickListener(ThreeTriosController features) {
    this.features = features;
    this.addMouseListener(new TTGClickListener());
  }

  private Dimension getLogicalSize() {
    int totalRows = model.getGrid().getRows();
    int totalCols = model.getGrid().getCols();
    int handWidth = 2; // Logical units for each player's hand
    int totalWidth = totalCols + 2 * handWidth;
    int totalHeight = totalRows;
    return new Dimension(totalWidth, totalHeight);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    System.out.println("paintComponent called");
    Graphics2D g2d = (Graphics2D) g.create();
    AffineTransform at = getLogicalToPhysical();
    System.out.println("AffineTransform: " + at);
    g2d.transform(at);
    drawGrid(g2d);
    drawPlayersHands(g2d);
  }

  private AffineTransform getLogicalToPhysical() {
    Dimension logicalDims = getLogicalSize();
    double scaleX = (double) this.getWidth() / logicalDims.getWidth();
    double scaleY = (double) this.getHeight() / logicalDims.getHeight();

    return AffineTransform.getScaleInstance(scaleX, scaleY);
  }

  /**
   * Draws the Three-Trios Grid based on the current game state.
   *
   * @param g2d The Graphics object for drawing.
   */
  private void drawGrid(Graphics2D g2d) {
    int totalRows = model.getGrid().getRows();
    int totalCols = model.getGrid().getCols();
    int handWidth = 2;
    int totalHeight = getLogicalSize().height;

    double gridYStart = (totalHeight - totalRows) / 2.0;

    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalCols; col++) {
        Cell cell = model.getGrid().getCell(row, col);
        int xPos = handWidth + col;
        double yPos = gridYStart + row;

        // Set cell color before filling
        setCellColor(g2d, cell.getCellType());
        g2d.fillRect(xPos, (int) yPos, 1, 1);

        // Draw border after filling
        g2d.setColor(Color.BLACK);
        g2d.drawRect(xPos, (int) yPos, 1, 1);

        // If there is a card in the cell, draw it
        if (cell.isOccupied()) {
          Card card = cell.getCard();
          drawCard(g2d, card, xPos, yPos, 1, 1, parseCardColor(cell.getOwner().getColor()));
        }
      }
    }
  }


  private Color parseCardColor(Player cardOwner) {
    if (cardOwner == null) {
      return null;
    }
    if (cardOwner == Player.RED) {
      return Color.RED;
    } else {
      return Color.BLUE;
    }
  }

  /**
   * Sets the color for each cell based on its type.
   */
  private void setCellColor(Graphics g, CellType cellType) {
    switch (cellType) {
      case CARD_CELL:
        g.setColor(Color.WHITE);
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
  private void drawPlayersHands(Graphics2D g2d) {
    int totalRows = model.getGrid().getRows();
    int totalCols = model.getGrid().getCols();
    int handWidth = 2;

    // Left player's hand at x from 0 to handWidth
    drawHand(g2d, model.getPlayers().get(0).getPlayerHand(), 0, handWidth, true);

    // Right player's hand at x from handWidth + totalCols to handWidth + totalCols + handWidth
    int rightHandXStart = handWidth + totalCols;
    drawHand(g2d, model.getPlayers().get(1).getPlayerHand(), rightHandXStart, handWidth, false);
  }

  /**
   * Draws a player's hand aligned with cell heights, at a fixed x-position.
   *
   * @param g2d          Graphics object for drawing.
   * @param hand         List of cards in the player's hand.
   * @param xStart       Fixed x-position for the hand.
   * @param handWidth    Width of the hand in logical units.
   * @param isLeftPlayer Flag to indicate which player's hand is being drawn.
   */
  private void drawHand(Graphics2D g2d, List<Card> hand, int xStart, int handWidth, boolean isLeftPlayer) {
    int totalRows = model.getGrid().getRows();

    for (int i = 0; i < hand.size(); i++) {
      int yPos = i % totalRows; // Adjust if hand has more cards than rows

      g2d.setColor(isLeftPlayer ? new Color(194, 95, 95, 255) : new Color(115, 148, 234));
      g2d.fillRect(xStart, yPos, handWidth, 1);
      g2d.setColor(Color.BLACK);
      g2d.drawRect(xStart, yPos, handWidth, 1);

      Card card = hand.get(i);
      drawCard(g2d, card, xStart, yPos, handWidth, 1, Color.BLACK);
    }
  }

  /**
   * Draws a card with attack values.
   *
   * @param g2d       Graphics object for drawing.
   * @param card      Card to be drawn.
   * @param xPos      X position in logical coordinates.
   * @param yPos      Y position in logical coordinates.
   * @param width     Width in logical units.
   * @param height    Height in logical units.
   * @param textColor Color for the attack values.
   */
  private void drawCard(Graphics2D g2d, Card card, double xPos, double yPos, double width, double height, Color textColor) {
    // Use a fixed font size
    float fontSize = 0.5f; // or any appropriate size

    Font originalFont = g2d.getFont();
    Font font = originalFont.deriveFont(fontSize);
    g2d.setFont(font);
    g2d.setColor(textColor);

    String north = formatAttackValue(card.getAttackValue(Direction.NORTH));
    String south = formatAttackValue(card.getAttackValue(Direction.SOUTH));
    String east = formatAttackValue(card.getAttackValue(Direction.EAST));
    String west = formatAttackValue(card.getAttackValue(Direction.WEST));

    // Compute positions
    float centerX = (float) (xPos + width / 2);
    float centerY = (float) (yPos + height / 2);

    FontMetrics metrics = g2d.getFontMetrics(font);
    int textHeight = metrics.getAscent();
    int textWidthN = metrics.stringWidth(north);
    int textWidthS = metrics.stringWidth(south);
    int textWidthE = metrics.stringWidth(east);
    int textWidthW = metrics.stringWidth(west);

    // North
    g2d.drawString(north, centerX - textWidthN / 2f, (float) yPos + textHeight);

    // South
    g2d.drawString(south, centerX - textWidthS / 2f, (float) (yPos + height) - 2);

    // East
    g2d.drawString(east, (float) (xPos + width) - textWidthE - 2, centerY + textHeight / 2f);

    // West
    g2d.drawString(west, (float) xPos + 2, centerY + textHeight / 2f);

    // Restore original font
    g2d.setFont(originalFont);
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
      try {
        Point evtPt = e.getPoint();
        System.err.println(evtPt);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
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
