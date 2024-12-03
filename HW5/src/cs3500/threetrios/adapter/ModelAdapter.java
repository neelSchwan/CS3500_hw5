package cs3500.threetrios.adapter;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.GameCell;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GameModelListener;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.GridPos;
import cs3500.threetrios.provider.model.ModelListener;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.model.ThreeTrios;


public class ModelAdapter implements ThreeTrios {
  private final GameModel model;
  private final List<ModelListener> listeners = new ArrayList<>();
  private final List<GameModelListener> adaptedListeners = new ArrayList<>();

  public ModelAdapter(GameModel model) {
    this.model = model;
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
    List<cs3500.threetrios.model.Card> hand = model.getCurrentPlayer().getPlayerHand();
    if (cardIdx < 0 || cardIdx >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index");
    }

    cs3500.threetrios.model.Card cardToPlace = hand.get(cardIdx);

    model.placeCard(pos.getRow(), pos.getCol(), cardToPlace);
  }

  /**
   * Adds a model listener (subscriber).
   *
   * @param listener the listener
   */
  @Override
  public void addModelListener(ModelListener listener) {
    listeners.add(listener);

    GameModelListener adaptedListener = new GameModelListener() {
      @Override
      public void onTurnChanged(GamePlayer currentPlayer) {
        listener.onTurnChanged(toProviderPlayer(currentPlayer));
      }

      @Override
      public void onGameStateUpdated() {
        // N/A WE DON'T HAVE THIS
      }

      @Override
      public void onGameOver(GamePlayer winner) {
        int winningScore;
        if (winner != null) {
          winningScore = model.getPlayerScore(winner);
        } else {
          winningScore = 0;
        }

        listener.onGameOver(
                winner == null ? null : toProviderPlayer(winner),
                winningScore
        );
      }
    };

    adaptedListeners.add(adaptedListener);
    model.addGameModelListener(adaptedListener);
  }

  /**
   * Removes a model listener.
   *
   * @param listener the listener
   */
  @Override
  public void removeModelListener(ModelListener listener) {
    int index = listeners.indexOf(listener);
    if (index != -1) {
      listeners.remove(index);
      adaptedListeners.remove(index);
    }
  }

  /**
   * Gets the current grid of the game as a 2d array.
   *
   * @return a 2D array representing the current grid state
   */
  @Override
  public Cell[][] getCurrentGrid() {
    cs3500.threetrios.model.GameGrid myGrid = model.getGrid();
    Cell[][] providerGrid = new Cell[myGrid.getRows()][myGrid.getCols()];

    for (int i = 0; i < myGrid.getRows(); i++) {
      for (int j = 0; j < myGrid.getCols(); j++) {
        GameCell myCell = myGrid.getCell(i, j);
        if (myCell == null || myCell.isHole()) {
          providerGrid[i][j] = null;
        } else {
          providerGrid[i][j] = new CellAdapter(myCell);
        }
      }
    }
    return providerGrid;
  }

  /**
   * Gets the current player's turn.
   *
   * @return the player whose turn it is
   */
  @Override
  public Player getTurn() {
    return toProviderPlayer(model.getCurrentPlayer());
  }

  // Helper method to convert to a provider player given my GamePlayer.
  private Player toProviderPlayer(GamePlayer gamePlayer) {
    if (gamePlayer.getColor() == cs3500.threetrios.model.Player.RED) {
      return Player.RED;
    }
    return Player.BLUE;
  }

  /**
   * Gets a copy of the specified player's hand.
   *
   * @param player the player whose hand is requested
   * @return a list of cards in the player's hand
   */
  @Override
  public List<Card> getHand(Player player) {

    GamePlayer matchingPlayer = null;
    for (GamePlayer gamePlayer : model.getPlayers()) {
      if (toProviderPlayer(gamePlayer) == player) {
        matchingPlayer = gamePlayer;
        break;
      }
    }

    if (matchingPlayer == null) {
      throw new IllegalArgumentException("Player not found");
    }

    // Convert the cards
    List<Card> convertedHand = new ArrayList<>();
    for (cs3500.threetrios.model.Card card : matchingPlayer.getPlayerHand()) {
      convertedHand.add(new CardAdapter(card));
    }

    return convertedHand;
  }

  /**
   * Determines the winner of the game if the game is over.
   *
   * @return the winning player, or null if it's a tie
   * @throws IllegalStateException if the game is not over yet
   */
  @Override
  public Player getWinner() {
    if (!model.isGameOver()) {
      throw new IllegalStateException("Game is not over yet");
    }

    GamePlayer winner = model.getWinner();
    return winner == null ? null : toProviderPlayer(winner);
  }
}
