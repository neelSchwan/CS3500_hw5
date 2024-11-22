package cs3500.threetrios.model;


import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.strategy.Move;
import cs3500.threetrios.strategy.ThreeTriosStrategy;
import cs3500.threetrios.view.GameEventListener;

public class AIPlayer implements GamePlayer {
  private final Player color;
  private final List<Card> hand;
  private final ThreeTriosStrategy strategy;
  private final GameModel gameModel;
  private GameEventListener eventListener;
  private Move currentMove;

  public AIPlayer(Player color, List<Card> hand, ThreeTriosStrategy strategy, GameModel gameModel) {
    this.color = color;
    this.hand = new ArrayList<>(hand);
    this.strategy = strategy;
    this.gameModel = gameModel;
  }

  public void setEventListener(GameEventListener listener) {
    this.eventListener = listener;
  }

  private void makeMove() {
    if (eventListener == null) {
      return;
    }
    currentMove = strategy.chooseMove(gameModel);
    int cardIndex = hand.indexOf(currentMove.getCard());
    eventListener.onCardSelected(cardIndex, this);
    eventListener.onCellClicked(currentMove.getRow(), currentMove.getCol());
  }

  @Override
  public Card chooseCard(int cardIndex) {
    if (currentMove == null) {
      makeMove();
    }
    return currentMove.getCard();
  }

  @Override
  public int[] choosePosition(int row, int col) {
    if (currentMove == null) {
      makeMove();
    }
    return new int[]{currentMove.getRow(), currentMove.getCol()};
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
    throw new UnsupportedOperationException("Cannot change AI player color");
  }

  @Override
  public void addCardToHand(Card card) {
    hand.add(card);
  }

  @Override
  public void removeCardFromHand(Card card) {
    hand.remove(card);
    currentMove = null;
  }
}