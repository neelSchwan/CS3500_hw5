package cs3500.threetrios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.adapter.ModelAdapter;
import cs3500.threetrios.adapter.ViewAdapter;
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
import cs3500.threetrios.provider.model.ThreeTrios;
import cs3500.threetrios.provider.view.ThreeTriosViewImpl;
import cs3500.threetrios.strategy.CornerStrategy;
import cs3500.threetrios.strategy.FlipMostStrategy;
import cs3500.threetrios.strategy.ThreeTriosStrategy;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.ThreeTriosGUIView;

/**
 * Run a Three Trios game.
 * Initializes the game by reading configurations, creating players, and setting up
 * the game model, view, and controller. It then starts the game.
 */
public class Main {

  /**
   * The main method initializes and starts the Three Trios game.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.err.println("Usage: java ThreeTrios <player1> <player2>");
      System.err.println("Player types: 'human' or 'ai:<strategy>'");
      System.err.println("Available strategies: 'corner', 'flipmost'");
      return;
    }

    // Step 1: Read configurations
    CardConfigReader cardReader = new CardConfigReader();
    GridConfigReader gridReader = new GridConfigReader();
    List<Card> deck = cardReader.readCards("HW5/src/resources/CardDb.txt");
    Grid grid = gridReader.readGridFromFile("HW5/src/resources/EasyTestingGridDb.txt");

    // Step 2: Initialize the model
    System.out.println("Initializing model...");
    GameModel model = new ThreeTriosModel(grid, deck);

    // Step 3: Create players and add to the model
    System.out.println("Creating players...");
    GamePlayer player1 = createPlayer(args[0], Player.RED, model);
    System.out.println("RED PLAYER: " + player1);
    GamePlayer player2 = createPlayer(args[1], Player.BLUE, model);
    System.out.println("BLUE PLAYER: " + player2);

    model.addPlayer(player1);
    model.addPlayer(player2);

    // Step 4: Initialize views and controllers
    System.out.println("Initializing view and controller...");
    GameView view1 = new ThreeTriosGUIView(model);

    // Create the model adapter for the provider's view
    ThreeTrios modelAdapter = new ModelAdapter(model);
    ThreeTriosViewImpl providerView = new ThreeTriosViewImpl(modelAdapter);

    // Create the adapter view for player 2
    GameView view2 = new ViewAdapter(providerView, player2);

    // Create controllers for both players
    GameController controller1 = new ThreeTriosController(model, view1, player1);
    GameController controller2 = new ThreeTriosController(model, view2, player2);

    model.startGame(0);
    // Step 5: Make views visible
    view1.makeVisible();
    view2.makeVisible();

  }

  /**
   * Creates a player instance based on the provided input string.
   * THIS IS EXPLAINED MORE IN THE README.
   *
   * @param input the player configuration string (e.g., "human" or "ai:corner").
   * @param color the color of the player (RED or BLUE).
   * @param model the game model.
   * @return the configured GamePlayer instance.
   * @throws IllegalArgumentException if the input is invalid.
   */
  private static GamePlayer createPlayer(String input, Player color, GameModel model) {
    if (input.equalsIgnoreCase("human")) {
      return new HumanPlayer(color, new ArrayList<>());
    } else if (input.startsWith("ai:")) {
      String strategyName = input.substring(3).toLowerCase();
      ThreeTriosStrategy strategy;

      switch (strategyName) {
        case "corner":
          strategy = new CornerStrategy();
          break;
        case "flipmost":
          strategy = new FlipMostStrategy();
          break;
        default:
          throw new IllegalArgumentException("Invalid strategy: " + strategyName);
      }

      return new AIPlayer(color, new ArrayList<>(), strategy, model);
    } else {
      throw new IllegalArgumentException("Invalid player type: " + input);
    }
  }

}
