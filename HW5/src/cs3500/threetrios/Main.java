package cs3500.threetrios;

import java.io.IOException;
import java.util.List;

import cs3500.threetrios.controller.GameController;
import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardConfigReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridConfigReader;
import cs3500.threetrios.model.HumanPlayerFactory;
import cs3500.threetrios.model.PlayerFactory;
import cs3500.threetrios.model.ThreeTriosModel;
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
    // Load configuration files
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    List<Card> deck = cardConfigReader.readCards("src/resources/CardDb.txt");
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    PlayerFactory playerFactory = new HumanPlayerFactory();
    GameModel model = new ThreeTriosModel(grid, playerFactory, deck);

    GamePlayer redPlayer = model.getPlayers().get(0);
    GamePlayer bluePlayer = model.getPlayers().get(1);

    // Initialize views for each player
    GameView redView = new ThreeTriosGUIView(model);
    GameView blueView = new ThreeTriosGUIView(model);

    // Initialize controllers for each player
    GameController redController = new ThreeTriosController(model, redView, redPlayer);
    GameController blueController = new ThreeTriosController(model, blueView, bluePlayer);

    model.startGame(0);

    redController.startGame();
    blueController.startGame();
    redView.makeVisible();
    blueView.makeVisible();
  }
}
//  /**
//   * Set up the game board by placing initial cards on the grid.
//   *
//   * @param model the game model to manage the game state.
//   * @param controller the game controller to handle game logic and view interactions.
//   */
//  private static void setupGame(GameModel model, GameController controller) {
//    int totalRows = model.getGrid().getRows();
//    int totalCols = model.getGrid().getCols();
//
//    for (int row = 0; row < totalRows; row++) {
//      for (int col = 0; col < totalCols; col++) {
//        if (model.getPlayers().get(0).getPlayerHand().size() == 3
//                && model.getPlayers().get(1).getPlayerHand().size() == 3) {
//          return;
//        }
//
//        if (!model.getGrid().getCell(row, col).isHole()
//                && !model.getCurrentPlayer().getPlayerHand().isEmpty()) {
//          controller.handlePlaceCard(row, col, model.getCurrentPlayer().getPlayerHand().get(0));
//        }
//      }
//    }
//  }
//}
