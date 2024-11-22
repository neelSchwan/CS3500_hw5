package cs3500.threetrios.controller;


/**
 * Interface for the game controller in Three Trios.
 * Responsible for managing communication between the model, view, and players.
 */
public interface GameController {

  /**
   * Updates the view to reflect the current game state.
   */
  void updateView();

  /**
   * Displays the winner and ends the game.
   */
  void endGame();
}
