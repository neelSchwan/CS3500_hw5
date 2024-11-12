package cs3500.threetrios.controller;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.view.ThreeTriosGUIView;

public class ThreeTriosGUIController implements ThreeTriosController {
  private final ThreeTriosGUIView view;
  private GameModel model;

  public ThreeTriosGUIController(ThreeTriosGUIView view) {
    if (view == null) {
      throw new IllegalArgumentException("view cannot be null");
    }
    this.view = view;
    view.addClickListener(this);
  }

  @Override
  public void playGame(GameModel model) { //could add seed to this.
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
    model.startGame(0);
    view.refresh();
  }

  @Override
  public void handleCellClick(int row, int col) {
    if (!model.isValidMove(row, col)) {
      throw new IllegalArgumentException("cant place card in specified cell.");
    }
    Card card = model.getCurrentPlayer().getPlayerHand().get(0);
    model.placeCard(row, col, card);
    view.refresh();
  }
}
