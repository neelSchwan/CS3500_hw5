package cs3500.threetrios.controller;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GameModelListener;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.GameEventListener;


/**
 * Implementation of the GameController interface for the Three Trios game.
 */
public class ThreeTriosController implements GameController, GameModelListener {

  private final GameModel model;
  private final GamePlayer player;
  private final GameView view;
  private Card selectedCard = null;
  private boolean isPlayerTurn = false;

  /**
   * Constructs a ThreeTriosController for the given model, player, and view.
   *
   * @param model  the game model.
   * @param view   the view for this player's perspective.
   * @param player the player this controller represents.
   */
  public ThreeTriosController(GameModel model, GameView view, GamePlayer player) {
    this.model = model;
    this.view = view;
    this.player = player;

    model.addGameModelListener(this);
    view.addGameEventListener(new GameEventListener() {
      @Override
      public void onCardSelected(int cardIndex, GamePlayer selectingPlayer) {
        handleCardSelection(cardIndex, selectingPlayer);
      }

      @Override
      public void onCellClicked(int row, int col) {
        handleCellClick(row, col);
      }
    });
  }

  /**
   * Starts the game loop for this controller.
   * Makes the view visible and updates the initial game state.
   */
  @Override
  public void startGame() {
    view.makeVisible();
    updateView();
  }

  /**
   * Handles card selection from the player's hand.
   *
   * @param cardIndex       the index of the selected card.
   * @param selectingPlayer the player selecting the card.
   */
  private void handleCardSelection(int cardIndex, GamePlayer selectingPlayer) {
    // Prevent selecting cards from opponent's hand
    if (!player.equals(selectingPlayer)) {
      view.displayMessage("You cannot select cards from the opponent's hand.");
      return;
    }

    if (!isPlayerTurn) {
      view.displayMessage("It's not your turn.");
      return;
    }

    // Validate the card index
    if (cardIndex < 0 || cardIndex >= player.getPlayerHand().size()) {
      view.displayMessage("Invalid card selection.");
      return;
    }

    // Select the card
    selectedCard = player.getPlayerHand().get(cardIndex);
    view.displayMessage("Selected card: " + selectedCard);
  }

  /**
   * Handles grid cell clicks for card placement.
   *
   * @param row the row index of the clicked cell.
   * @param col the column index of the clicked cell.
   */
  private void handleCellClick(int row, int col) {
    // Ensure the player has selected a card
    if (selectedCard == null) {
      view.displayMessage("Select a card before choosing a cell.");
      return;
    }

    // Ensure it's the player's turn
    if (!isPlayerTurn) {
      view.displayMessage("It's not your turn.");
      return;
    }

    try {
      if (model.isValidMove(row, col)) {
        model.placeCard(row, col, selectedCard);
        selectedCard = null;

        updateView();

        if (model.isGameOver()) {
          endGame();
        }
      } else {
        view.displayMessage("Invalid move. Cell is not valid.");
      }
    } catch (IllegalArgumentException | IllegalStateException e) {

      view.displayMessage("Error: " + e.getMessage());
    }
  }

  /**
   * Updates the view with the current game state and turn information.
   */
  @Override
  public void updateView() {
    view.updateView();

    if (isPlayerTurn) {
      view.displayMessage("It's your turn, " + player.getColor() + " player.");
    } else {
      view.displayMessage("Waiting for the other player...");
    }
  }

  /**
   * Ends the game and announces the winner.
   */
  @Override
  public void endGame() {
    GamePlayer winner = model.getWinner();
    if (winner == null) {
      view.showWinner("It's a tie!");
    } else {
      view.showWinner("Winner: " + winner.getColor());
    }
    view.displayMessage("Game Over!");
  }

  // --------- GameModelListener Methods ---------

  /**
   * Called when the current player changes.
   *
   * @param currentPlayer the new current player
   */
  @Override
  public void onTurnChanged(GamePlayer currentPlayer) {
    isPlayerTurn = currentPlayer.equals(player);
    view.setViewEnabled(isPlayerTurn);
    updateView();
  }

  /**
   * Called when the game state updates like after a move is made.
   */
  @Override
  public void onGameStateUpdated() {
    view.updateView();
  }

  /**
   * Called when the game ends.
   *
   * @param winner the winner of the game, or null if it's a tie
   */
  @Override
  public void onGameOver(GamePlayer winner) {
    if (winner == null) {
      view.showWinner("It's a tie!");
    } else {
      view.showWinner("Winner: " + winner.getColor());
    }
    view.displayMessage("Game Over!");
  }
}
