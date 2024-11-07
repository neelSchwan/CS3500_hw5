package cs3500.threetrios.controller;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.view.ThreeTriosView;

public class ThreeTriosGUIController implements ThreeTriosController {
  private final ThreeTriosView view;
  private ThreeTriosModel model;

  public ThreeTriosGUIController(ThreeTriosView view) {
    if (view == null)  {
      throw new IllegalArgumentException("view cannot be null");
    }
    this.view = view;
    view.addClickListener(this);
  }

  @Override
  public void playGame() {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
    view.makeVisible();
  }

  @Override
  public void handleCellClick(int row, int col) {
    model.placeCard(row, col, card);
    view.refresh();
  }


  @Override
  public void playGame(GameModel model) {

  }

  @Override
  public void handleCellClick(int row, int col) {

  }
}
