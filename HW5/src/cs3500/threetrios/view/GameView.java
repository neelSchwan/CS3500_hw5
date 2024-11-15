package cs3500.threetrios.view;

/**
 * Represents the main view class for the Three Trios game.
 * Provides methods to display, update, and reset the game view.
 * Can also listen for user input / interactions, such as a mouse click.
 */
public interface GameView {
  /**
   * Updates the view to reflect the current state of the game.
   * Should be called whenever the game state changes.
   */
  void updateView();

  /**
   * Resets the view to the initial game state.
   * Useful for starting a new game.
   */
  void resetView();

  /**
   * Displays a message showing the winner of the game.
   *
   * @param winner the name or identifier of the winning player
   */
  void showWinner(String winner);

  /**
   * Adds a listener to handle game events triggered by the view, such as
   * cell clicks or card selections.
   *
   * @param listener a GameEventListener that will respond to view events
   */
  void addGameEventListener(GameEventListener listener);

  /**
   * Makes the view visible.
   */
  void makeVisible();

  /**
   * Displays a message to the player.
   *
   * @param message the message to display
   */
  void displayMessage(String message);
}
