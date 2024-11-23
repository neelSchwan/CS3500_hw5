package cs3500.threetrios.view;

import cs3500.threetrios.model.GamePlayer;

/**
 * Mock implementation of GameView for testing.
 * Logs all method calls and provides methods to simulate user interactions.
 */
public class MockGameView implements GameView {
  private final StringBuilder log;
  private GameEventListener listener;
  private boolean isEnabled;
  private GamePlayer activePlayer;

  /**
   * Constructs a mock view that logs all operations.
   *
   * @param log StringBuilder to record operations
   */
  public MockGameView(StringBuilder log) {
    this.log = log;
    this.isEnabled = true;
  }

  @Override
  public void updateView() {
    log.append("View updated\n");
  }

  @Override
  public void resetView() {
    log.append("View reset\n");
  }

  @Override
  public void showWinner(String winner) {
    log.append("Winner shown: ").append(winner).append("\n");
  }

  @Override
  public void addGameEventListener(GameEventListener listener) {
    this.listener = listener;
    log.append("Game event listener added\n");
  }

  @Override
  public void makeVisible() {
    log.append("View made visible\n");
  }

  @Override
  public void displayMessage(String message) {
    log.append("Message displayed: ").append(message).append("\n");
  }

  @Override
  public void setViewEnabled(boolean enabled) {
    this.isEnabled = enabled;
    log.append("View enabled set to: ").append(enabled).append("\n");
  }

  @Override
  public void updateActivePlayer(GamePlayer currentPlayer) {
    this.activePlayer = currentPlayer;
    log.append("Active player updated to: ").append(currentPlayer.getColor()).append("\n");
  }

  /**
   * Simulates a user selecting a card from their hand.
   *
   * @param cardIndex index of the card in the hand
   * @param player player selecting the card
   */
  public void simulateCardSelection(int cardIndex, GamePlayer player) {
    log.append("Simulating card selection: index=").append(cardIndex)
            .append(", player=").append(player.getColor()).append("\n");

    if (listener != null && isEnabled) {
      listener.onCardSelected(cardIndex, player);
    }
  }

  /**
   * Simulates a user clicking a cell on the grid.
   *
   * @param row row of the clicked cell
   * @param col column of the clicked cell
   */
  public void simulateCellClick(int row, int col) {
    log.append("Simulating cell click at: (").append(row).append(",").append(col).append(")\n");

    if (listener != null && isEnabled) {
      listener.onCellClicked(row, col);
    }
  }

  /**
   * Gets the content of the log.
   *
   * @return the log content
   */
  public String getLog() {
    return log.toString();
  }

  /**
   * Checks if the view is currently enabled.
   *
   * @return true if the view is enabled, false otherwise
   */
  public boolean isEnabled() {
    return isEnabled;
  }

  /**
   * Gets the currently active player.
   *
   * @return the active player
   */
  public GamePlayer getActivePlayer() {
    return activePlayer;
  }

  /**
   * Gets the current event listener.
   *
   * @return the current listener
   */
  public GameEventListener getListener() {
    return listener;
  }
}