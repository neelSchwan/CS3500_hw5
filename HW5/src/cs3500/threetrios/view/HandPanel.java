package cs3500.threetrios.view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Direction;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Player;

/**
 * Class for visualizing the hand components of each player.
 * This class will help render a players hand to the screen.
 */
public class HandPanel extends JPanel implements GameComponent {

  private final GamePlayer player;
  private GameEventListener eventListener;
  private int selectedCardIndex = -1;
  private boolean isInteractive = true;
  private GamePlayer activePlayer;

  /**
   * Constructor for creating a HandPanel given a GamePlayer.
   */
  public HandPanel(GamePlayer player) {
    this.player = player;
    this.addMouseListener(new CardClickListener());
  }

  public void setGameEventListener(GameEventListener eventListener) {
    this.eventListener = eventListener;
  }

  public void setInteractive(boolean interactive) {
    this.isInteractive = interactive;
  }

  public void setActivePlayer(GamePlayer activePlayer) {
    this.activePlayer = activePlayer;
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
    int cardCount = player.getPlayerHand().size();

    if (cardCount == 0) {
      g2d.setColor(Color.LIGHT_GRAY);
      g2d.drawString("No cards to display", getWidth() / 2 - 50, getHeight() / 2);
      return;
    }

    int cardHeight = getHeight() / cardCount;
    for (int i = 0; i < cardCount; i++) {
      Card card = player.getPlayerHand().get(i);
      int yPos = i * cardHeight;
      drawCard(g2d, card, 0, yPos, getWidth(), cardHeight, i == selectedCardIndex);
    }
  }


  private void drawCard(Graphics2D g2d, Card card, int xPos, int yPos,
                        int width, int height, boolean isSelected) {
    Color blueColor = new Color(123, 153, 220);
    Color redColor = new Color(239, 109, 109);
    Color color = player.getColor() == Player.RED ? redColor : blueColor;
    g2d.setColor(isSelected ? color.darker() : color);
    g2d.fillRect(xPos, yPos, width, height);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(xPos, yPos, width, height);

    int centerX = xPos + width / 2;
    int centerY = yPos + height / 2;

    drawCardText(g2d, String.valueOf(card.getAttackValue(Direction.NORTH)),
            centerX, yPos, 0, 10); // North
    drawCardText(g2d, String.valueOf(card.getAttackValue(Direction.SOUTH)),
            centerX, yPos + height, 0, -10); // South
    drawCardText(g2d, String.valueOf(card.getAttackValue(Direction.WEST)),
            xPos, centerY, 10, 0); // West
    drawCardText(g2d, String.valueOf(card.getAttackValue(Direction.EAST)),
            xPos + width, centerY, -10, 0); // East
  }

  /**
   * Helper method to draw directional text on the card.
   */
  private void drawCardText(Graphics2D g2d, String text,
                            int xCenter, int yCenter, int offsetX, int offsetY) {
    FontMetrics metrics = g2d.getFontMetrics();
    int textWidth = metrics.stringWidth(text);
    int textHeight = metrics.getHeight();

    int textX = xCenter + offsetX - textWidth / 2;
    int textY = yCenter + offsetY + textHeight / 4;
    if (text.equals("10")) {
      g2d.drawString("A", textX, textY);
    } else {
      g2d.drawString(text, textX, textY);
    }
  }

  private class CardClickListener extends MouseAdapter {
    @Override
    public void mouseReleased(MouseEvent e) {
      if (!isInteractive || !player.equals(activePlayer)) {
        return;
      }

      int cardHeight = getHeight() / player.getPlayerHand().size();
      int clickedIndex = e.getY() / cardHeight;

      if (clickedIndex >= 0 && clickedIndex < player.getPlayerHand().size()) {
        if (clickedIndex == selectedCardIndex) {
          selectedCardIndex = -1;
        } else {
          selectedCardIndex = clickedIndex;
        }
        if (eventListener != null) {
          eventListener.onCardSelected(selectedCardIndex, player);
        }
        repaint();
      }
    }
  }


  /**
   * Refreshes the display of the component based on the latest game state.
   */
  @Override
  public void refresh() {
    repaint();
  }
}
