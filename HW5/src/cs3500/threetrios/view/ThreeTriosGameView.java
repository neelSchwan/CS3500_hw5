package cs3500.threetrios.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardConfigReader;
import cs3500.threetrios.model.Cell;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridConfigReader;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosModel;

public class ThreeTriosGameView implements ThreeTriosView {

  private final ThreeTriosModel model;

  public ThreeTriosGameView(ThreeTriosModel model) {
    this.model = model;
  }

  /**
   * Generates a textual / string representation of the current game state
   *
   * @param model three-trios model that is used for getting the current game state.
   * @return String representation of the current game state.
   */
  @Override
  public String render(ThreeTriosModel model) {
    StringBuilder sb = new StringBuilder();

    sb.append("Player: ").append(model.getCurrentPlayer()).append("\n");

    renderGrid(model, sb);

    sb.append("Hand: ").append("\n");
    List<Card> hand = model.getPlayerHand(model.getCurrentPlayer());
    sb.append(renderPlayerHand(hand));
    return sb.toString();
  }

  private void renderGrid(ThreeTriosModel model, StringBuilder sb) {
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

  private String renderPlayerHand(List<Card> hand) {
    StringBuilder sb = new StringBuilder();
    for (Card card : hand) {
      sb.append(card.toString()).append("\n"); // Each card on a new line
    }
    return sb.toString();
  }

  /**
   * Outputs the current game state to the console.
   *
   * @param model three-trios model that is used for getting the current game state.
   */
  @Override
  public void display(ThreeTriosModel model) {
  }

}
