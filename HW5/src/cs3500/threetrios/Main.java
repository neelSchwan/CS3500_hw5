package cs3500.threetrios;

import java.util.List;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.ThreeTriosGUIController;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardConfigReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridConfigReader;
import cs3500.threetrios.model.HumanPlayerFactory;
import cs3500.threetrios.model.PlayerFactory;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.ThreeTriosGameGUIView;
import cs3500.threetrios.view.ThreeTriosView;

public class Main {

  public static void main(String[] args) {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    List<Card> deck = cardConfigReader.readCards("src/resources/CardDb.txt");
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    PlayerFactory playerFactory = new HumanPlayerFactory();

    GameModel model = new ThreeTriosModel(grid, playerFactory, deck);
    ThreeTriosView view = new ThreeTriosGameGUIView();
    ThreeTriosController controller = new ThreeTriosGUIController(view);
    controller.playGame(model);
  }
}
