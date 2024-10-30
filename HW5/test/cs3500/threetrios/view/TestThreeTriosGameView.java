package cs3500.threetrios.view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.model.Card;
import cs3500.threetrios.model.CardConfigReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.Grid;
import cs3500.threetrios.model.GridConfigReader;
import cs3500.threetrios.model.Player;
import cs3500.threetrios.model.ThreeTriosModel;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the ThreeTriosGameView class.
 * This class sets up a test environment for verifying the functionality of the ThreeTrios game view.
 */
public class TestThreeTriosGameView {

  private GameModel model;
  //private List<Card> deck;
  //private Grid grid;
  //private Map<Player, List<Card>> hands;

  @Before
  public void setUp() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    List<Card> deck = cardConfigReader.readCards("src/resources/CardDb.txt");
    Grid grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    Map<Player, List<Card>> hands = new HashMap<>();
    hands.put(Player.RED, new ArrayList<>());
    hands.put(Player.BLUE, new ArrayList<>());

    model = new ThreeTriosModel(grid, hands, deck);
  }

  @Test
  public void testRender() throws IOException {
    model.startGame(0);
    StringBuilder output = new StringBuilder();
    ThreeTriosView view = new ThreeTriosGameView(model, output);
    view.render();

    String expectedOutput = "Player: RED\n"
            + "_ _         _\n"
            + "_   _       _\n"
            + "_     _     _\n"
            + "_       _   _\n"
            + "_         _ _\n"
            + "Hand: \n"
            + "darius [N: 7, S: 9, E: 8, W: 4]\n"
            + "ekko [N: 8, S: 9, E: 4, W: 6]\n"
            + "katarina [N: 10, S: 7, E: 6, W: 5]\n"
            + "vayne [N: 9, S: 3, E: 10, W: 6]\n"
            + "warwick [N: 9, S: 7, E: 6, W: 5]\n"
            + "malphite [N: 9, S: 5, E: 8, W: 7]\n"
            + "jinx [N: 6, S: 5, E: 9, W: 7]\n"
            + "garen [N: 6, S: 8, E: 5, W: 4]\n";

    assertEquals(expectedOutput, output.toString());
  }
}
