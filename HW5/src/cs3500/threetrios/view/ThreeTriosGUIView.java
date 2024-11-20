package cs3500.threetrios.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * The ThreeTriosGUIView class represents the main GUI component for the three-trios program.
 * Essentially, this class combines all the panels (grid and hand) together and adds them into a
 * "main" panel.
 */
public class ThreeTriosGUIView extends JFrame implements GameView {
  private final HandPanel redHandPanel;
  private final HandPanel blueHandPanel;
  private final GridPanel gridPanel;
  private final JLabel messageLabel;

  /**
   * Constructor for creating a GUI view given a model to determine the game state.
   *
   * @param model model for getting game state (grid, and players).
   */
  public ThreeTriosGUIView(ReadonlyThreeTriosModel model) {
    setTitle("ThreeTrios Game");
    this.setSize(600, 400);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    redHandPanel = new HandPanel(model.getPlayers().get(0));
    blueHandPanel = new HandPanel(model.getPlayers().get(1));
    gridPanel = new GridPanel(model.getGrid());
    messageLabel = new JLabel("Welcome to Three Trios!", SwingConstants.CENTER);
    messageLabel.setPreferredSize(new Dimension(0, 30));
    messageLabel.setOpaque(true);
    messageLabel.setBackground(Color.LIGHT_GRAY);

    redHandPanel.setPreferredSize(new Dimension(75, 0));
    blueHandPanel.setPreferredSize(new Dimension(75, 0));

    redHandPanel.setMinimumSize(new Dimension(100, 300));
    blueHandPanel.setMinimumSize(new Dimension(100, 300));
    gridPanel.setMinimumSize(new Dimension(300, 300));

    add(redHandPanel, BorderLayout.WEST);
    add(blueHandPanel, BorderLayout.EAST);
    add(gridPanel, BorderLayout.CENTER);
    add(messageLabel, BorderLayout.NORTH);
  }

  /**
   * Updates the view to reflect the current state of the game.
   * Should be called whenever the game state changes.
   */
  @Override
  public void updateView() {
    redHandPanel.refresh();
    blueHandPanel.refresh();
    gridPanel.refresh();
  }

  /**
   * Resets the view to the initial game state.
   * Useful for starting a new game.
   */
  @Override
  public void resetView() {
    updateView();
  }

  /**
   * Displays a message showing the winner of the game.
   *
   * @param winner the name or identifier of the winning player
   */
  @Override
  public void showWinner(String winner) {
    JOptionPane.showMessageDialog(this, "Winner: " + winner);
  }

  /**
   * Adds a listener to handle game events triggered by the view, such as
   * cell clicks or card selections.
   *
   * @param listener a GameEventListener that will respond to view events
   */
  @Override
  public void addGameEventListener(GameEventListener listener) {
    redHandPanel.setGameEventListener(listener);
    blueHandPanel.setGameEventListener(listener);
    gridPanel.setGameEventListener(listener);
  }

  /**
   * Makes the view visible.
   */
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Displays a message to the player.
   *
   * @param message the message to display
   */
  @Override
  public void displayMessage(String message) {
    messageLabel.setText(message);
  }

  @Override
  public void setViewEnabled(boolean enabled) {
    redHandPanel.setEnabled(enabled);
    blueHandPanel.setEnabled(enabled);
    gridPanel.setEnabled(enabled);

    // Forward to individual components
    redHandPanel.setInteractive(enabled);
    blueHandPanel.setInteractive(enabled);
    gridPanel.setInteractive(enabled);
  }

  @Override
  public void updateActivePlayer(GamePlayer currentPlayer) {
    redHandPanel.setActivePlayer(currentPlayer);
    blueHandPanel.setActivePlayer(currentPlayer);
  }
}
