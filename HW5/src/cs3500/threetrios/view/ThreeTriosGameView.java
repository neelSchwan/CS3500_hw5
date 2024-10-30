package cs3500.threetrios.view;

import java.io.IOException;
import java.util.List;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.Grid;

/**
 * Class for creating a textual representation of the game's current state.
 */
public class ThreeTriosGameView implements ThreeTriosView {

  private final GameModel model;
  private final Appendable out;

  /**
   * Constructs a ThreeTriosGameView.
   *
   * @param model the game model to render.
   * @param out   the appendable to render the output to.
   */
  public ThreeTriosGameView(GameModel model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  /**
   * Generates a textual / string representation of the current game state and appends it
   * to the Appendable.
   *
   * @param model the three-trios model used for getting the current game state.
   */
  @Override
  public void render(GameModel model) throws IOException {
    StringBuilder sb = new StringBuilder();

    sb.append("Player: ").append(model.getCurrentPlayer()).append("\n");
    renderGrid(model, sb);

    sb.append("Hand: ").append("\n");
    List<Card> hand = model.getPlayerHand(model.getCurrentPlayer());
    sb.append(renderPlayerHand(hand));

    out.append(sb.toString());
  }

  /**
   * Renders the grid to the StringBuilder.
   */
  private void renderGrid(GameModel model, StringBuilder sb) {
    Grid grid = model.getGrid();
    for (int i = 0; i < grid.getRows(); i++) {
      for (int j = 0; j < grid.getCols(); j++) {
        Cell cell = grid.getCell(i, j);
        if (cell.isHole()) {
          sb.append(' ');
        } else if (!cell.isOccupied()) {
          sb.append('_');
        } else {
          sb.append(cell.getOwner().toString().substring(0, 1).toUpperCase());
        }
        if (j < grid.getCols() - 1) {
          sb.append(" ");
        }
      }
      sb.append("\n");
    }
  }

  /**
   * Renders the player's hand.
   */
  private String renderPlayerHand(List<Card> hand) {
    StringBuilder sb = new StringBuilder();
    for (Card card : hand) {
      sb.append(card.toString()).append("\n"); // Each card on a new line
    }
    return sb.toString();
  }

  /**
   * Outputs the current game state to the appendable.
   */
  @Override
  public void display(GameModel model) throws IOException {
    render(model);
  }

}
