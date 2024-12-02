package cs3500.threetrios.adapter;

import java.util.List;

import cs3500.threetrios.model.GameCell;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.provider.model.*;


public class ModelAdapter implements ThreeTrios {
  private final GameModel model;

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

  }

  /**
   * Removes a model listener.
   *
   * @param listener the listener
   */
  @Override
  public void removeModelListener(ModelListener listener) {

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

    for(int i = 0; i < myGrid.getRows(); i++) {
      for(int j = 0; j < myGrid.getCols(); j++) {
        GameCell myCell = myGrid.getCell(i, j);
        if(myCell == null || myCell.isHole()) {
          providerGrid[i][j] = null;
        } else {
          providerGrid[i][j] = new
        }
      }
    }
  }

  /**
   * Gets the current player's turn.
   *
   * @return the player whose turn it is
   */
  @Override
  public Player getTurn() {
    return null;
  }

  /**
   * Gets a copy of the specified player's hand.
   *
   * @param player the player whose hand is requested
   * @return a list of cards in the player's hand
   */
  @Override
  public List<Card> getHand(Player player) {
    return List.of();
  }

  /**
   * Determines the winner of the game if the game is over.
   *
   * @return the winning player, or null if it's a tie
   * @throws IllegalStateException if the game is not over yet
   */
  @Override
  public Player getWinner() {
    return null;
  }
}
