package cs3500.threetrios.adapter;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardConfigReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.GamePlayer;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridConfigReader;
import cs3500.threetrios.model.HumanPlayer;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.provider.view.ThreeTriosViewImpl;
import cs3500.threetrios.provider.view.ThreeTriosView;
import cs3500.threetrios.view.GameView;
import cs3500.threetrios.view.ThreeTriosGUIView;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapterTest {
  public static void main(String[] args) {
    GameModel model = getGameModel();

    // Player 1 uses your original view
    GamePlayer player1 = model.getPlayers().get(0);
    GameView gameView1 = new ThreeTriosGUIView(model);
    ThreeTriosController controller1 = new ThreeTriosController(model, gameView1, player1);

    // Player 2 uses the provided view, adapted to your system
    GamePlayer player2 = model.getPlayers().get(1);
    ThreeTriosView providerView = new ThreeTriosViewImpl(new ModelAdapter(model));
    GameView adapter2 = new ProviderViewAdapter(providerView); // Adapter connects provided view
    ThreeTriosController controller2 = new ThreeTriosController(model, adapter2, player2);

    // Start the game
    model.startGame(0);

    // Make both views visible
    gameView1.makeVisible();
    adapter2.makeVisible();
  }

  private static GameModel getGameModel() {
    try {
      CardConfigReader cardReader = new CardConfigReader();
      GridConfigReader gridReader = new GridConfigReader();
      List<Card> mockDeck = cardReader.readCards("HW5/src/resources/CardDb.txt");
      Grid mockGrid = gridReader.readGridFromFile("HW5/src/resources/EasyTestingGridDb.txt");

      // Create the game model
      GameModel model = new ThreeTriosModel(mockGrid, mockDeck);
      GamePlayer player1 = new HumanPlayer(Player.RED, new ArrayList<>());
      GamePlayer player2 = new HumanPlayer(Player.BLUE, new ArrayList<>());

      model.addPlayer(player1);
      model.addPlayer(player2);
      return model;
    } catch (Exception e) {
      throw new RuntimeException("Error initializing the game model: " + e.getMessage(), e);
    }
  }
}
