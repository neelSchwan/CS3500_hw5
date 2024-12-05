package cs3500.threetrios.adapter;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GameModelListener;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.GridPos;
import cs3500.threetrios.provider.model.ModelListener;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.model.ThreeTrios;

/**
 * Adapter to convert/bridge the provided 'ThreeTrios' model interface with our existing
 * 'GameModel' interface by converting the game state and interactions between the two systems.
 * This allows the provider's view and logic to work with the existing model
 * implementation.
 */
public class ModelAdapter implements ThreeTrios {
  private final GameModel modelAdaptee;
  private ModelListener modelListener;

  /**
   * Constructor for a ModelAdapter that wraps the existing GameModel instance.
   * This adapter translates method calls and data structures between the GameModel
   * and the provider's ThreeTrios model interface.
   *
   * @param modelAdaptee the original GameModel instance from our implementation.
   */
  public ModelAdapter(GameModel modelAdaptee) {
    this.modelAdaptee = modelAdaptee;
  }

  /**
   * Places the card at the given index in the player's hand whose turn it currently is
   * at the given position on the grid.
   *
   * @param pos     the position in the grid to place the card at.
   * @param cardIdx an index in the current players hand.
   */
  @Override
  public void placeCard(GridPos pos, int cardIdx) {
    if (modelAdaptee.isGameOver()) {
      throw new IllegalStateException("Cannot place card when game isn't started");
    }

    int row = pos.getRow();
    int col = pos.getCol();

    cs3500.threetrios.model.Card card = modelAdaptee.getCurrentPlayer().chooseCard(cardIdx);
    modelAdaptee.placeCard(row, col, card);
  }

  /**
   * Adds a model listener (subscriber).
   *
   * @param listener the listener
   */
  @Override
  public void addModelListener(ModelListener listener) {
    this.modelListener = listener;
    modelAdaptee.addGameModelListener(new GameModelListener() {
      /**
       * Called when the current player changes.
       *
       * @param currentPlayer the new current player
       */
      @Override
      public void onTurnChanged(GamePlayer currentPlayer) {
        if (modelListener != null) {
          modelListener.onTurnChanged(convertGamePlayerToProvider(currentPlayer));
        }
      }

      /**
       * Called when the game state updates like after a move is made.
       */
      @Override
      public void onGameStateUpdated() {
        // N/A as it's not needed to work with the provided view.
      }

      /**
       * Called when the game ends.
       *
       * @param winner the winner of the game, or null if it's a tie
       */
      @Override
      public void onGameOver(GamePlayer winner) {
        if (modelListener != null) {
          modelListener.onGameOver(convertGamePlayerToProvider(winner),
                  modelAdaptee.getPlayerScore(winner));
        }
      }
    });
  }

  /**
   * Removes a model listener.
   *
   * @param listener the listener
   */
  @Override
  public void removeModelListener(ModelListener listener) {
    this.modelListener = null; // we dont have remove
  }

  /**
   * Gets the current grid of the game as a 2d array.
   *
   * @return a 2D array representing the current grid state
   */
  @Override
  public Cell[][] getCurrentGrid() {
    int rows = modelAdaptee.getGrid().getRows();
    int cols = modelAdaptee.getGrid().getCols();

    Cell[][] grid = new Cell[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        grid[row][col] = new CellAdapter(modelAdaptee.getGrid().getCell(row, col));
      }
    }

    return grid;
  }

  /**
   * Gets the current player's turn.
   *
   * @return the player whose turn it is
   */
  @Override
  public Player getTurn() {
    GamePlayer currentPlayer = modelAdaptee.getCurrentPlayer();
    if (currentPlayer == null) {
      return Player.RED; // default red, doesn't work without this for some reason.
    }
    return currentPlayer.getColor() == cs3500.threetrios.model.Player.RED
            ? Player.RED : Player.BLUE;
  }

  /**
   * Gets a copy of the specified player's hand.
   *
   * @param player the player whose hand is requested
   * @return a list of cards in the player's hand
   */
  @Override
  public List<Card> getHand(Player player) {
    GamePlayer gamePlayer = providerPlayerToGamePlayer(player);

    List<Card> cardsList = new ArrayList<>();
    for (cs3500.threetrios.model.Card card : gamePlayer.getPlayerHand()) {
      CardAdapter cardAdapter = new CardAdapter(card);
      cardsList.add(cardAdapter);
    }
    return cardsList;
  }

  private GamePlayer providerPlayerToGamePlayer(Player providerPlayer) {
    for (GamePlayer gamePlayer : modelAdaptee.getPlayers()) {
      if ((gamePlayer.getColor() == cs3500.threetrios.model.Player.RED
              && providerPlayer == Player.RED)
              || (gamePlayer.getColor() == cs3500.threetrios.model.Player.BLUE
              && providerPlayer == Player.BLUE)) {
        return gamePlayer;
      }
    }
    throw new IllegalArgumentException("Player " + providerPlayer + " not found");
  }

  private Player convertGamePlayerToProvider(GamePlayer gamePlayer) {
    return gamePlayer.getColor() == cs3500.threetrios.model.Player.RED ? Player.RED : Player.BLUE;
  }


  /**
   * Determines the winner of the game if the game is over.
   *
   * @return the winning player, or null if it's a tie
   * @throws IllegalStateException if the game is not over yet
   */
  @Override
  public Player getWinner() {
    GamePlayer winner = modelAdaptee.getWinner();
    if (winner == null) {
      return null;
    } else if (winner.getColor() == cs3500.threetrios.model.Player.BLUE) {
      return Player.BLUE;
    } else if (winner.getColor() == cs3500.threetrios.model.Player.RED) {
      return Player.RED;
    }
    return null;
  }
}
