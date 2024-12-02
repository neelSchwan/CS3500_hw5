package cs3500.threetrios.provider.view;

import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.model.ReadOnlyThreeTrios;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * ThreeTriosViewImpl is an implementation of the ThreeTriosView.
 * It shows the left side as the Red player's hand and the right side as the
 * Blue player's hand.  The middle is the board where you can place cards.
 */
public class ThreeTriosViewImpl extends JFrame implements ThreeTriosView {
  private final ReadOnlyThreeTrios model;
  private final List<ViewListener> listeners;
  private HandPanel redHandPanel;
  private HandPanel blueHandPanel;
  private BoardPanel boardPanel;

  /**
   * Constructor that will initialize everything.
   *
   * @param model the read only model
   */
  public ThreeTriosViewImpl(ReadOnlyThreeTrios model) {
    super("ThreeTrios Game");
    this.model = model;
    this.listeners = new ArrayList<>();

    updateTitle();

    // Set the default close operation and layout
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Initialize panels
    boardPanel = new BoardPanel(model);
    redHandPanel = new HandPanel(model, Player.RED);
    blueHandPanel = new HandPanel(model, Player.BLUE);

    // Set up listeners for hand panels
    redHandPanel.setHandPanelListener(new cs3500.view.HandPanelListener() {
      @Override
      public void cardSelected(int cardIndex) {
        notifyCardSelected(cardIndex);
      }
    });

    blueHandPanel.setHandPanelListener(new cs3500.view.HandPanelListener() {
      @Override
      public void cardSelected(int cardIndex) {
        notifyCardSelected(cardIndex);
      }
    });

    // Set up listener for board panel
    boardPanel.setBoardPanelListener(new BoardPanelListener() {
      @Override
      public void positionSelected(int row, int col) {
        notifyPositionSelected(row, col);
      }
    });

    // Add panels to the frame
    this.add(redHandPanel, BorderLayout.WEST);
    this.add(boardPanel, BorderLayout.CENTER);
    this.add(blueHandPanel, BorderLayout.EAST);

    // Pack and set visibility
    this.pack();
    this.setVisible(true);
  }

  @Override
  public void addViewListener(ViewListener listener) {
    listeners.add(listener);
  }

  @Override
  public void removeViewListener(ViewListener listener) {
    listeners.remove(listener);
  }

  /**
   * Makes the view visible.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Refreshes the view when the game is updated.
   */
  @Override
  public void refresh() {
    this.repaint();
    updateTitle();
  }

  // Method to update the title based on the current player
  private void updateTitle() {
    Player currentPlayer = model.getTurn();
    this.setTitle("Current player: " + currentPlayer);
  }

  /**
   * Highlights the selected card in the view.
   *
   * @param cardIndex index of the card selected
   * @param player    player color
   */
  @Override
  public void highlightSelectedCard(int cardIndex, Player player) {
    if (player == Player.BLUE) {
      redHandPanel.highlightCard(cardIndex);
    } else {
      blueHandPanel.highlightCard(cardIndex);
    }
  }

  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void updateStatus(String status) {
    this.setTitle("Current player: " + model.getTurn() + " - " + status);
  }

  /**
   * Enables or disables user input in the view components based on the player's turn.
   *
   * @param enabled true to enable input, false to disable
   */
  public void setInputEnabled(boolean enabled) {
    Player currentPlayer = model.getTurn();

    if (currentPlayer == Player.RED) {
      redHandPanel.setEnabled(enabled);
      blueHandPanel.setEnabled(false); // Always disable opponent's hand panel
    } else if (currentPlayer == Player.BLUE) {
      blueHandPanel.setEnabled(enabled);
      redHandPanel.setEnabled(false);
    }

    // Enable or disable the board panel
    boardPanel.setEnabled(enabled);

    redHandPanel.repaint();
    blueHandPanel.repaint();
    boardPanel.repaint();
  }

  /**
   * Displays a dialog box to inform the player that the game is over, showing the winner and the
   * score.
   *
   * @param message The message to display to the user.
   */
  @Override
  public void showGameOver(String message) {
    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    setInputEnabled(false);
  }

  private void notifyCardSelected(int cardIndex) {
    for (ViewListener listener : listeners) {
      listener.onCardSelected(cardIndex);
    }
  }

  private void notifyPositionSelected(int row, int col) {
    for (ViewListener listener : listeners) {
      listener.onPosSelected(row, col);
    }
  }

  /**
   * Used the bring the current players window to the front.
   */
  @Override
  public void bringToFront() {
    SwingUtilities.invokeLater(() -> {
      setVisible(true);
      toFront();
      requestFocus();
      setAlwaysOnTop(true);
      setAlwaysOnTop(false);
    });
  }

}
