package cs3500.threetrios.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.view.ThreeTriosGameView;
import cs3500.threetrios.view.ThreeTriosView;

import static org.junit.Assert.assertEquals;

public class TestThreeTriosGameView {

  private GameModel model;
  private List<Card> deck;
  private Grid grid;
  private Map<Player, List<Card>> hands;

  @Before
  public void setUp() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    deck = cardConfigReader.readCards("src/resources/CardDb.txt");
    grid = gridConfigReader.readGridFromFile("src/resources/GridDb.txt");

    hands = new HashMap<>();
    hands.put(Player.RED, new ArrayList<>());
    hands.put(Player.BLUE, new ArrayList<>());

    model = new ThreeTriosModel(grid, hands, deck);
  }

  @Test
  public void testRender() {
    model.startGame(0);
    ThreeTriosView view = new ThreeTriosGameView(model);
    assertEquals(view.render(model), "Player: RED\n" +
            "_ _         _\n" +
            "_   _       _\n" +
            "_     _     _\n" +
            "_       _   _\n" +
            "_         _ _\n" +
            "Hand: \n" +
            "darius [N: 7, S: 9, E: 8, W: 4]\n" +
            "ekko [N: 8, S: 9, E: 4, W: 6]\n" +
            "katarina [N: 10, S: 7, E: 6, W: 5]\n" +
            "vayne [N: 9, S: 3, E: 10, W: 6]\n" +
            "warwick [N: 9, S: 7, E: 6, W: 5]\n" +
            "malphite [N: 9, S: 5, E: 8, W: 7]\n" +
            "jinx [N: 6, S: 5, E: 9, W: 7]\n" +
            "garen [N: 6, S: 8, E: 5, W: 4]\n");
  }

}
