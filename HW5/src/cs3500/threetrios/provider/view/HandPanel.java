package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Direction;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.model.ReadOnlyThreeTrios;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The hand panel will display the cards of each players hands.
 */
public class HandPanel extends JPanel {
  private final ReadOnlyThreeTrios model;
  private final Player player;
  private int cardHeight;
  private int selectedCardIndex = -1;
  private cs3500.view.HandPanelListener listener;

  /**
   * Constructs the hand panel.
   *
   * @param model  The read only model
   * @param player The current player
   */
  public HandPanel(ReadOnlyThreeTrios model, Player player) {
    this.model = model;
    this.player = player;
    this.addMouseListener(new HandMouseListener());
    this.setBackground(Color.BLACK);

    // Ensure the panel repaints when resized
    this.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(java.awt.event.ComponentEvent evt) {
        repaint();
      }
    });
  }

  public void setHandPanelListener(cs3500.view.HandPanelListener listener) {
    this.listener = listener;
  }

  /**
   * Dummy documentation for this, which highlights a card.
   *
   * @param index index in the hand of the card to highlight.
   */
  public void highlightCard(int index) {
    this.selectedCardIndex = index;
    repaint();
  }

  /**
   * Gets the dimension of the card.
   *
   * @return The dimensions of the width and height
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(150, 600);
  }

  /**
   * Draws every card in the deck to the display.
   *
   * @param g the graphics object to display
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    List<Card> hand = model.getHand(player);
    int numCards = hand.size();

    int cardWidth = getWidth();

    // Calculate cardHeight based on panel height and number of cards
    int availableHeight = getHeight();
    cardHeight = availableHeight / Math.max(numCards, 1);

    // Recalculate total required height
    int totalCardHeight = cardHeight * numCards;

    // Adjust cardHeight if total height is less than available height
    if (totalCardHeight < availableHeight) {
      cardHeight = availableHeight / numCards;
      cardHeight = Math.min(cardHeight, 200);
    }

    // Draw each card
    for (int i = 0; i < numCards; i++) {
      int x = 0;
      int y = i * cardHeight;

      // Set card color based on player
      if (player == Player.RED) {
        g2.setColor(new Color(255, 200, 200)); // Light red
      } else {
        g2.setColor(new Color(200, 200, 255)); // Light blue
      }
      g2.fillRect(x, y, cardWidth, cardHeight);

      // Draw card border
      g2.setColor(Color.BLACK);
      g2.drawRect(x, y, cardWidth, cardHeight);

      // Highlight selected card
      if (i == selectedCardIndex && selectedCardIndex != -1) {
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(x, y, cardWidth, cardHeight);
        g2.setStroke(new BasicStroke(1));
      }

      // Get card attack values
      Card card = hand.get(i);
      int northValue = card.getValueOf(Direction.NORTH);
      int southValue = card.getValueOf(Direction.SOUTH);
      int eastValue = card.getValueOf(Direction.EAST);
      int westValue = card.getValueOf(Direction.WEST);

      // Convert values to display representations
      String northText = getDisplayValue(northValue);
      String southText = getDisplayValue(southValue);
      String eastText = getDisplayValue(eastValue);
      String westText = getDisplayValue(westValue);

      // Adjust font size based on card height
      int fontSize = (int) (cardHeight * 0.35);
      g2.setFont(new Font("Arial", Font.BOLD, fontSize));
      FontMetrics fm = g2.getFontMetrics();
      g2.setColor(Color.BLACK);

      // Calculate center of the card
      int centerX = x + cardWidth / 2;
      int centerY = y + cardHeight / 2;

      // Position and draw North value
      int textWidth = fm.stringWidth(northText);
      int textHeight = fm.getAscent();
      int nx = centerX - textWidth / 2;
      int ny = y + textHeight + 5;
      g2.drawString(northText, nx, ny);

      // Position and draw South value
      textWidth = fm.stringWidth(southText);
      int sx = centerX - textWidth / 2;
      int sy = y + cardHeight - 5;
      g2.drawString(southText, sx, sy);

      // Position and draw East value
      textWidth = fm.stringWidth(eastText);
      int ex = x + cardWidth - textWidth - 5;
      int ey = centerY + textHeight / 2 - 5;
      g2.drawString(eastText, ex, ey);

      // Position and draw West value
      int wx = x + 5;
      int wy = centerY + textHeight / 2 - 5;
      g2.drawString(westText, wx, wy);
    }
  }

  // Mouse listener for the hand
  private class HandMouseListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      if (!isEnabled()) {
        return;
      }
      int index = e.getY() / cardHeight;
      List<Card> hand = model.getHand(player);

      if (index >= 0 && index < hand.size()) {
        if (listener != null) {
          listener.cardSelected(index);
        }
        // Handle card selection/deselection
        if (selectedCardIndex == index) {
          // Deselect the card
          selectedCardIndex = -1;
          System.out.println("Deselected card at index " + index + " for player " + player);
        } else {
          // Select the new card
          selectedCardIndex = index;
          System.out.println("Selected card at index " + index + " for player " + player);
        }
        repaint();
      } else {
        System.out.println("Click outside the hand.");
      }
    }
  }

  private String getDisplayValue(int value) {
    return (value == 10) ? "A" : Integer.toString(value);
  }

}
