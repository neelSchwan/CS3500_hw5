package cs3500.threetrios;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.GameController;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.AIPlayer;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardConfigReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridConfigReader;
import cs3500.threetrios.model.HumanPlayer;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.strategy.CornerStrategy;
import cs3500.threetrios.strategy.ThreeTriosStrategy;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.ThreeTriosGUIView;

/**
 * Run a Three Trios game interactively.
 * Initializes the game by reading configurations, creating players, and setting up
 * the game model, view, and controller. It then starts the game.
 * Players are instantiated as either human or AI-controlled, and are added to the game model.
 * The game uses a graphical user interface implemented in {@link ThreeTriosGUIView} to interact
 * with players, and AI-controlled players follow a specified strategy.
 */
public class Main {

  /**
   * The main method initializes and starts the Three Trios game.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    CardConfigReader cardReader = new CardConfigReader();
    GridConfigReader gridReader = new GridConfigReader();
    List<Card> deck = cardReader.readCards("HW5/src/resources/CardDb.txt");
    Grid grid = gridReader.readGridFromFile("HW5/src/resources/EasyTestingGridDb.txt");

    GameModel model = new ThreeTriosModel(grid, deck);

    GamePlayer humanPlayer = new HumanPlayer(Player.RED, new ArrayList<>());
    ThreeTriosStrategy aiStrategy = new CornerStrategy();
    GamePlayer aiPlayer = new AIPlayer(Player.BLUE, new ArrayList<>(), aiStrategy, model);

    model.addPlayer(humanPlayer);
    model.addPlayer(aiPlayer);

    GameView humanView = new ThreeTriosGUIView(model);
    GameView aiView = new ThreeTriosGUIView(model);

    GameController humanController = new ThreeTriosController(model, humanView, humanPlayer);
    GameController aiController = new ThreeTriosController(model, aiView, aiPlayer);
    model.startGame(0);

  }
}
