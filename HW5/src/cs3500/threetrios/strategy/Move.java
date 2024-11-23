package cs3500.threetrios.strategy;

import cs3500.threetrios.model.Card;

/**
 * Move class for containing a move.
 * A move consists of a row, col, and Card to place. The row and col
 */
public class Move {
  private int row;
  private int col;
  private Card card;
  private int score;

  /**
   * Constructs a move with the specified row, column, card, and score.
   *
   * @param row the row index where the card is to be placed
   * @param col the column index where the card is to be placed
   * @param card the card to place on the grid
   * @param score the score associated with this move
   */
  public Move(int row, int col, Card card, int score) {
    this.row = row;
    this.col = col;
    this.card = card;
    this.score = score;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Card getCard() {
    return card;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Determines if this move is better than another move.
   * A move is considered better if it has a higher score. If the scores are equal, the move with
   * the smaller row index is better. If both scores and row indices are equal, the move with the
   * smaller column index is better.
   *
   * @param otherMove the other move to compare with
   * @return {@code true} if this move is better than the other move; {@code false} otherwise
   */
  public boolean isBetterThan(Move otherMove) {
    if (this.score > otherMove.score) {
      return true;
    } else if (this.score == otherMove.score) {
      return this.row < otherMove.row || (this.row == otherMove.row && this.col < otherMove.col);
    }
    return false;
  }

}
