package cs3500.threetrios;

import java.io.IOException;
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
import cs3500.threetrios.strategy.FlipMostStrategy;
import cs3500.threetrios.strategy.ThreeTriosStrategy;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.ThreeTriosGUIView;

/**
 * The {@code Main} class initializes the game by reading configuration files for the deck of cards
 * and the game grid, sets up the {@code GameModel}, and launches the game view.
 */
public class Main {

  /**
   * The main method initializes game components and starts the Three Trios Game.
   *
   * @throws IOException if there is an error reading from the configuration files.
   */
  public static void main(String[] args) throws IOException {
    CardConfigReader cardReader = new CardConfigReader();
    GridConfigReader gridReader = new GridConfigReader();
    List<Card> deck = cardReader.readCards("HW5/src/resources/CardDb.txt");
    Grid grid = gridReader.readGridFromFile("HW5/src/resources/EasyTestingGridDb.txt");

    // Create players
    GamePlayer humanPlayer = new HumanPlayer(Player.RED, new ArrayList<>());
    ThreeTriosStrategy aiStrategy = new FlipMostStrategy();
    GamePlayer humanPlayer2 = new HumanPlayer(Player.BLUE, new ArrayList<>());

    // Create model
    GameModel model = new ThreeTriosModel(grid, deck);

    model.addPlayer(humanPlayer);
    model.addPlayer(humanPlayer2);
    // Create views
    GameView humanView = new ThreeTriosGUIView(model);
    GameView aiView = new ThreeTriosGUIView(model);

    // Create controllers
    GameController humanController = new ThreeTriosController(model, humanView, humanPlayer);
    GameController aiController = new ThreeTriosController(model, aiView, humanPlayer2);

    // Start the game
    model.startGame(0);
    humanController.startGame();
    aiController.startGame();
    humanView.makeVisible();
    aiView.makeVisible();
  }
}
