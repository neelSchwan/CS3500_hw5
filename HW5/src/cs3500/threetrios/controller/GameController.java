package cs3500.threetrios.controller;

import cs3500.threetrios.model.GameModelListener;
import cs3500.threetrios.model.GamePlayer;

/**
 * Interface for the game controller in Three Trios.
 * Responsible for managing communication between the model, view, and players.
 * Handles game flow, user interactions, and state updates.
 */
public interface GameController extends GameModelListener {

  /**
   * Updates the view to reflect the current game state.
   * Called when game state changes (after moves, turns, etc.).
   */
  void updateView();

  /**
   * Handles the end of game condition.
   * Displays the winner and final game state.
   */
  void endGame();

  /**
   * Handles a player's card selection from their hand.
   *
   * @param cardIndex       index of the selected card
   * @param selectingPlayer the player making the selection
   */
  void handleCardSelection(int cardIndex, GamePlayer selectingPlayer);

  /**
   * Handles a player's attempt to place a card on the grid.
   *
   * @param row the row where the player wants to place the card
   * @param col the column where the player wants to place the card
   */
  void handleCellClick(int row, int col);

}