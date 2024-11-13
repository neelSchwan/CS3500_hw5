package cs3500.threetrios.controller;

import cs3500.threetrios.model.GameCard;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.view.ThreeTriosView;

public class ThreeTriosGUIController implements ThreeTriosController {
  private final ThreeTriosView view;
  private GameModel model;

  public ThreeTriosGUIController(ThreeTriosView view) {
    if (view == null)  {
      throw new IllegalArgumentException("view cannot be null");
    }
    this.view = view;
    view.addClickListener(this);
  }

  @Override
  public void playGame(GameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
    //model.startGame(0); //seed can be changed
    view.makeVisible();
  }

  @Override
  public void handleCellClick(int row, int col) {
    Card card = new GameCard("wasd",2,2,2,2);
    model.placeCard(row, col, card);
    view.refresh();
  }

}
