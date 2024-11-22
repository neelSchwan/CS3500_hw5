package cs3500.threetrios.model;

import cs3500.threetrios.strategy.Move;
import cs3500.threetrios.strategy.ThreeTriosStrategy;
import cs3500.threetrios.view.GameEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an AI player that emits events based on its strategy.
 */
public class AIPlayer implements GamePlayer {
  private final Player color;
  private final List<Card> hand;
  private final ThreeTriosStrategy strategy;
  private final ReadonlyThreeTriosModel model;
  private GameEventListener eventListener;

  /**
   * Constructs an AIPlayer with a color, initial hand, strategy, and model.
   *
   * @param color    the player's color
   * @param hand     the initial hand of cards
   * @param strategy the strategy the AI uses to play
   * @param model    the readonly game model for the AI to analyze
   */
  public AIPlayer(Player color, List<Card> hand, ThreeTriosStrategy strategy, ReadonlyThreeTriosModel model) {
    this.color = color;
    this.hand = new ArrayList<>(hand);
    this.strategy = strategy;
    this.model = model;
  }

  /**
   * Sets the event listener to emit player actions.
   *
   * @param listener the listener to set
   */
  public void setEventListener(GameEventListener listener) {
    this.eventListener = listener;
  }

  /**
   * Makes a move using the strategy and emits the corresponding events.
   */
  public void playTurn() {
    if (eventListener == null) {
      throw new IllegalStateException("Event listener is not set for AIPlayer.");
    }

    // Use the strategy to compute the next move
    Move move = strategy.chooseMove((GameModel) model);

    // Emit events for card selection and cell placement
    int cardIndex = hand.indexOf(move.getCard());
    if (cardIndex == -1) {
      throw new IllegalStateException("Card chosen by strategy is not in the AI's hand.");
    }

    eventListener.onCardSelected(cardIndex, this);
    eventListener.onCellClicked(move.getRow(), move.getCol());
  }

  @Override
  public Card chooseCard(int cardIndex) {
    return hand.get(cardIndex);
  }

  @Override
  public int[] choosePosition(int row, int col) {
    return new int[]{row, col};
  }

  @Override
  public Player getColor() {
    return color;
  }

  @Override
  public List<Card> getPlayerHand() {
    return new ArrayList<>(hand);
  }

  @Override
  public void setColor(Player color) {
    throw new UnsupportedOperationException("AIPlayer color cannot be changed.");
  }

  @Override
  public void addCardToHand(Card card) {
    hand.add(card);
  }

  @Override
  public void removeCardFromHand(Card card) {
    hand.remove(card);
  }
}
