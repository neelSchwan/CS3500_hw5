package cs3500.threetrios.view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
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

  /**
   * Constructor for creating a HandPanel given a GamePlayer.
   */
  public HandPanel(GamePlayer player) {
    this.player = player;
    this.addMouseListener(new CardClickListener()); //TODO: IMPL CARD CLICK LISTENER TAKES IN GAMEEVENTLISTENER
  }

  public void setGameEventListener(GameEventListener eventListener) {
    this.eventListener = eventListener;
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

    int cardHeight = getHeight() / cardCount;
    for (int i = 0; i < cardCount; i++) {
      Card card = player.getPlayerHand().get(i);
      int yPos = i * cardHeight;
      drawCard(g2d, card, 0, yPos, getWidth(), cardHeight, i == selectedCardIndex);
    }
  }

  private void drawCard(Graphics2D g2d, Card card, int xPos, int yPos, int width, int height, boolean isSelected) {
    Color blueColor = new Color(123, 153, 220);
    Color redColor = new Color(239, 109, 109);
    Color color = player.getColor() == Player.RED ? redColor : blueColor;
    g2d.setColor(isSelected ? color.darker() : color);
    g2d.fillRect(xPos, yPos, width, height);
    g2d.setColor(Color.BLACK);
    g2d.drawRect(xPos, yPos, width, height);
    g2d.drawString(card.getAttackValue(Direction.NORTH) + "", xPos + width / 2, yPos + 15);  // Example
  }


  private class CardClickListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {

      int clickedIndex = e.getY() / (getHeight() / player.getPlayerHand().size());
      if (clickedIndex == selectedCardIndex) {
        selectedCardIndex = -1;
      } else {
        selectedCardIndex = clickedIndex;
        if (eventListener != null) {
          eventListener.onCardSelected(selectedCardIndex, player);
        }
      }
      repaint();
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
