package cs3500.threetrios.controller;

import cs3500.threetrios.model.AIPlayer;
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
    if (model == null || view == null || player == null) {
      throw new IllegalArgumentException("Args cannot be null");
    }

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

    if (player instanceof AIPlayer) {
      ((AIPlayer) player).setEventListener(new GameEventListener() {
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
    view.makeVisible();
    updateView();
  }

  @Override
  public void handleCardSelection(int cardIndex, GamePlayer selectingPlayer) {
    if (!player.equals(selectingPlayer)) {
      view.displayMessage("You cannot select cards from the opponent's hand.");
      return;
    }

    if (!model.getCurrentPlayer().equals(selectingPlayer)) {
      view.displayMessage("You cannot select cards from the opponent's hand.");
      return;
    }

    if (!isPlayerTurn) {
      view.displayMessage("It's not your turn.");
      return;
    }

    if (cardIndex < 0 || cardIndex >= player.getPlayerHand().size()) {
      view.displayMessage("Invalid card selection.");
      return;
    }

    selectedCard = player.getPlayerHand().get(cardIndex);
    view.displayMessage("Selected card: " + selectedCard);
  }

  @Override
  public void handleCellClick(int row, int col) {
    if (selectedCard == null) {
      view.displayMessage("Select a card before choosing a cell.");
      return;
    }


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

  @Override
  public void updateView() {
    view.updateView();

    if (isPlayerTurn) {
      view.displayMessage("It's your turn, " + player.getColor() + " player.");
    } else {
      view.displayMessage("Waiting for the other player...");
    }
  }

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
    view.updateActivePlayer(currentPlayer);
    updateView();

    if (isPlayerTurn && player instanceof AIPlayer) {
      ((AIPlayer) player).playTurn();
    }
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
