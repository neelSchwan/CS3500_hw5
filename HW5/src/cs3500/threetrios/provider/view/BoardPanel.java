package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.ReadOnlyThreeTrios;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The BoardPanel displays the board in the middle of the window.
 */
public class BoardPanel extends JPanel {
  private final ReadOnlyThreeTrios model;
  private int cellWidth;
  private int cellHeight;
  private cs3500.view.BoardPanelListener listener;

  /**
   * The constructor for the BoardPanel.
   *
   * @param model a ReadOnlyThreeTrios model
   */
  public BoardPanel(ReadOnlyThreeTrios model) {
    this.model = model;
    this.addMouseListener(new BoardMouseListener());
    this.setBackground(Color.BLACK);
  }

  public void setBoardPanelListener(cs3500.view.BoardPanelListener listener) {
    this.listener = listener;
  }

  /**
   * Gets the preferred size of each square.
   *
   * @return a dimension with width and height
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(600, 600);
  }

  /**
   * Paints the board.
   *
   * @param g the graphics object to display
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    // Get grid dimensions
    Cell[][] grid = model.getCurrentGrid();
    int rows = grid.length;
    int cols = grid[0].length;

    // Calculate cell size
    cellWidth = getWidth() / cols;
    cellHeight = getHeight() / rows;

    // Draw the grid cells
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        Cell cell = grid[row][col];
        int x = col * cellWidth;
        int y = row * cellHeight;

        // Draw cell background
        if (cell.isCardCell()) {
          g2.setColor(new Color(182, 182, 13)); // Valid cell
        } else {
          g2.setColor(new Color(148, 138, 138)); // Hole cell
        }
        g2.fillRect(x, y, cellWidth, cellHeight);

        // Draw cell border
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, cellWidth, cellHeight);

        // Draw card if present
        if (cell.hasCard()) {
          if (cell.getOwnerName().equals("RED")) {
            g2.setColor(new Color(255, 200, 200)); // Red
          } else if (cell.getOwnerName().equals("BLUE")) {
            g2.setColor(new Color(200, 200, 255)); // Blue
          } else {
            g2.setColor(Color.WHITE); // Default color
          }
          g2.fillRect(x, y, cellWidth, cellHeight);

          int northValue = cell.getCardValueOf(Direction.NORTH);
          int southValue = cell.getCardValueOf(Direction.SOUTH);
          int eastValue = cell.getCardValueOf(Direction.EAST);
          int westValue = cell.getCardValueOf(Direction.WEST);

          // Convert values to display representations
          String northText = getDisplayValue(northValue);
          String southText = getDisplayValue(southValue);
          String eastText = getDisplayValue(eastValue);
          String westText = getDisplayValue(westValue);

          // Optionally, draw card details or symbols
          g2.setColor(Color.BLACK);
          g2.setFont(new Font("Arial", Font.BOLD, (int) (cellHeight * 0.35)));
          FontMetrics fm = g2.getFontMetrics();

          // Calculate center of the card
          int centerX = x + cellWidth / 2;
          int centerY = y + cellHeight / 2;

          // Position and draw North value
          int textWidth = fm.stringWidth(northText);
          int textHeight = fm.getAscent();
          int nx = centerX - textWidth / 2;
          int ny = y + textHeight + 5;
          g2.drawString(northText, nx, ny);

          // Position and draw South value
          textWidth = fm.stringWidth(southText);
          int sx = centerX - textWidth / 2;
          int sy = y + cellHeight - 5;
          g2.drawString(southText, sx, sy);

          // Position and draw East value
          textWidth = fm.stringWidth(eastText);
          int ex = x + cellWidth - textWidth - 5;
          int ey = centerY + textHeight / 2 - 5;
          g2.drawString(eastText, ex, ey);

          // Position and draw West value
          int wx = x + 5;
          int wy = centerY + textHeight / 2 - 5;
          g2.drawString(westText, wx, wy);
        }
      }
    }
  }

  // Mouse listener for the board
  private class BoardMouseListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      int col = e.getX() / cellWidth;
      int row = e.getY() / cellHeight;

      // Validate positions
      Cell[][] grid = model.getCurrentGrid();
      if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
        if (listener != null) {
          listener.positionSelected(row, col);
        }
        GridPos2d pos = new GridPos2d(row, col);
        System.out.println("Grid cell clicked at: " + pos);
      } else {
        System.out.println("Click outside the grid.");
      }
    }
  }

  private String getDisplayValue(int value) {
    return (value == 10) ? "A" : Integer.toString(value);
  }
}
