package cs3500.threetrios.strategy;

public class Move {
  private int row;
  private int col;
  private Card card;
  private int score;

  public Move(int row, int col, Card card) {
    this.row = row;
    this.col = col;
    this.card = card;
    this.score = 0;
  }

  // Constructor with score
  public Move(int row, int col, Card card, int score) {
    this.row = row;
    this.col = col;
    this.card = card;
    this.score = score;
  }

  // Getters
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

  public boolean isBetterThan(Move otherMove) {
    if (this.score > otherMove.score) {
      return true;
    } else if (this.score == otherMove.score) {
      return this.row < otherMove.row || (this.row == otherMove.row && this.col < otherMove.col);
    }
    return false;
  }

}
