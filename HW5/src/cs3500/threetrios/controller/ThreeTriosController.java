package cs3500.threetrios.controller;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.GameEventListener;

/**
 * Implementation of the GameController interface for the Three Trios game.
 */
public class ThreeTriosController implements GameController {

  private final GameModel model;
  private final GamePlayer player;
  private final GameView view;
  private Card selectedCard = null;

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

    // Register as a listener for game events from the view
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
    if (!player.equals(selectingPlayer)) {
      view.displayMessage("It's not your turn to select a card.");
      return;
    }

    if (cardIndex < 0 || cardIndex >= player.getPlayerHand().size()) {
      view.displayMessage("Invalid card selection.");
      return;
    }

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
    if (selectedCard == null) {
      view.displayMessage("Select a card before choosing a cell.");
      return;
    }

    if (!model.getCurrentPlayer().equals(player)) {
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
   * Updates the view with the current game state.
   */
  @Override
  public void updateView() {
    view.updateView();
    if (model.getCurrentPlayer().equals(player)) {
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
}
