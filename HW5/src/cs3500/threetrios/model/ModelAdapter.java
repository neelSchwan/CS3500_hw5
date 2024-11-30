package cs3500.threetrios.model;

import cs3500.threetrios.provider.model.Card;
import cs3500.threetrios.provider.model.Cell;
import cs3500.threetrios.provider.model.GridPos;
import cs3500.threetrios.provider.model.ModelListener;
import cs3500.threetrios.provider.model.Player;
import cs3500.threetrios.provider.model.ThreeTrios;

import java.util.List;

public class ModelAdapter implements ThreeTrios { //TODO: ADAPT THEIR STUFF (CARD, ETC..)
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
        return new Cell[0][];
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
