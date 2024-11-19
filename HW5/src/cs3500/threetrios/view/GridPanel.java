package cs3500.threetrios.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.Player;

public class GridPanel extends JPanel implements GameComponent {

  private final Grid grid;
  private GameEventListener eventListener;
  private Point selectedCell = null;
  private boolean isInteractive = true;

  public GridPanel(Grid grid) {
    this.grid = grid;
    this.addMouseListener(new CellClickListener());
  }

  public void setGameEventListener(GameEventListener eventListener) {
    this.eventListener = eventListener;
  }

  public void setInteractive(boolean interactive) {
    this.isInteractive = interactive;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    render(g2d);
  }

  /**
   * Renders the component using the specified Graphics2D object.
   * This method should be called by the component's paint or paintComponent method.
   *
   * @param g2d the graphics object to use for rendering
   */
  @Override
  public void render(Graphics2D g2d) {
    drawGrid(g2d);
    if (selectedCell != null) {
      highlightSelectedCell(g2d);
    }
  }

  private void drawGrid(Graphics2D g2d) {
    int cellWidth = getWidth() / grid.getCols();
    int cellHeight = getHeight() / grid.getRows();
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        drawCell(g2d, row, col, cellWidth, cellHeight, isCellSelected(row, col));
      }
    }
  }

  private void drawCell(Graphics2D g2d, int row, int col, int cellWidth, int cellHeight, boolean isSelected) {
    int x = col * cellWidth;
    int y = row * cellHeight;

    if (grid.getCell(row, col).isOccupied()) {
      g2d.setColor(grid.getCell(row, col).getOwner().getColor() == Player.RED ? Color.RED : Color.BLUE);
      g2d.fillRect(x, y, cellWidth, cellHeight);
    } else if (grid.getCell(row, col).getCellType() == CellType.CARD_CELL) {
      g2d.setColor(Color.YELLOW);
      g2d.fillRect(x, y, cellWidth, cellHeight);
    } else {
      g2d.setColor(isSelected ? Color.GREEN : Color.LIGHT_GRAY);
      g2d.fillRect(x, y, cellWidth, cellHeight);
    }

    g2d.setColor(Color.BLACK);
    g2d.drawRect(x, y, cellWidth, cellHeight);

    if (grid.getCell(row, col).isOccupied()) {
      Card card = grid.getCell(row, col).getCard();
      g2d.setColor(Color.WHITE);
      int centerX = x + cellWidth / 2;
      int centerY = y + cellHeight / 2;

      drawCellText(g2d, String.valueOf(card.getAttackValue(Direction.NORTH)), centerX, y, 0, 10); // North
      drawCellText(g2d, String.valueOf(card.getAttackValue(Direction.SOUTH)), centerX, y + cellHeight, 0, -10); // South
      drawCellText(g2d, String.valueOf(card.getAttackValue(Direction.WEST)), x, centerY, 10, 0); // West
      drawCellText(g2d, String.valueOf(card.getAttackValue(Direction.EAST)), x + cellWidth, centerY, -10, 0); // East
    }
  }


  /**
   * Helper method to draw directional text on the grid cell.
   */
  private void drawCellText(Graphics2D g2d, String text, int xCenter, int yCenter, int offsetX, int offsetY) {
    FontMetrics metrics = g2d.getFontMetrics();
    int textWidth = metrics.stringWidth(text);
    int textHeight = metrics.getHeight();

    int textX = xCenter + offsetX - textWidth / 2;
    int textY = yCenter + offsetY + textHeight / 4;

    g2d.drawString(text, textX, textY);
  }

  private void highlightSelectedCell(Graphics2D g2d) {
    int cellWidth = getWidth() / grid.getCols();
    int cellHeight = getHeight() / grid.getRows();

    int x = selectedCell.y * cellWidth;
    int y = selectedCell.x * cellHeight;

    g2d.setColor(Color.GREEN);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawRect(x, y, cellWidth, cellHeight);
  }

  /**
   * Checks if a given cell is currently selected.
   *
   * @param row the row index
   * @param col the column index
   * @return true if the cell is selected, false otherwise
   */
  private boolean isCellSelected(int row, int col) {
    return selectedCell != null && selectedCell.x == row && selectedCell.y == col;
  }

  private class CellClickListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {

      if (!isInteractive) {
        return; // Ignore clicks when interactivity is disabled
      }

      int cellWidth = getWidth() / grid.getCols();
      int cellHeight = getHeight() / grid.getRows();

      int col = e.getX() / cellWidth;
      int row = e.getY() / cellHeight;
      if (row >= 0 && row < grid.getRows() && col >= 0 && col < grid.getCols()) {
        if (grid.getCell(row, col).getCellType() != CellType.HOLE_CELL) {
          if (e.getClickCount() == 2) {
            if (selectedCell != null && selectedCell.x == row && selectedCell.y == col) {
              selectedCell = null;
            }
          } else {
            selectedCell = new Point(row, col);
            if (eventListener != null) {
              eventListener.onCellClicked(row, col);
            }
          }
        }
        repaint();
      }
    }
  }


  /**
   * Refreshes the display of the component, forcing it to repaint itself based on the latest game state.
   */
  @Override
  public void refresh() {
    repaint();
  }
}

