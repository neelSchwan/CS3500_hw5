package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.threetrios.view.ThreeTriosGameView;
import cs3500.threetrios.view.ThreeTriosView;

/**
 * Additional test cases for the ThreeTriosModel class.
 */
public class AdditionalThreeTriosModelTests {

  private GameModel model;
  private Grid grid;

  /**
   * Initializes the test setup with a game model, deck of cards, grid, and player hands.
   * This method runs before each test to ensure consistency between model tests.
   */
  @Before
  public void setUp() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    List<Card> deck = cardConfigReader.readCards("HW5" + File.separator
            + "src" + File.separator + "resources" + File.separator + "CardDb.txt");
    grid = gridConfigReader.readGridFromFile("HW5" + File.separator + "src" + File.separator
            + "resources" + File.separator + "GridDb.txt");

    PlayerFactory playerFactory = new HumanPlayerFactory();

    model = new ThreeTriosModel(grid, playerFactory, deck);
    model.startGame(0);
  }

  /**
   * Test that gridSize returns the correct size.
   */
  @Test
  public void testGridSize() {
    int expectedSize = grid.getRows() + grid.getCols();
    Assert.assertEquals(expectedSize, model.gridSize());
  }

  /**
   * Test retrieving the contents of an occupied cell.
   */
  @Test
  public void testCellContentsOccupiedCell() {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    Card cardToPlace = currentPlayer.getPlayerHand().get(0);

    model.placeCard(0, 0, cardToPlace);

    Card retrievedCard = model.cellContents(0, 0);
    Assert.assertEquals(cardToPlace, retrievedCard);
  }

  /**
   * Test retrieving the contents of an unoccupied cell.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCellContentsUnoccupiedCell() {
    model.cellContents(0, 0);
  }

  /**
   * Test getCellOwner for an occupied cell.
   */
  @Test
  public void testGetCellOwnerOccupiedCell() {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    Card cardToPlace = currentPlayer.getPlayerHand().get(0);

    model.placeCard(0, 0, cardToPlace);

    GamePlayer owner = model.getCellOwner(0, 0);
    Assert.assertEquals(currentPlayer, owner);
  }

  /**
   * Test getCellOwner for an unoccupied cell.
   */
  @Test
  public void testGetCellOwnerUnoccupiedCell() {
    GamePlayer owner = model.getCellOwner(0, 0);
    Assert.assertNull(owner);
  }

  /**
   * Test isValidMove with a valid move.
   */
  @Test
  public void testIsValidMoveValid() {
    boolean isValid = model.isValidMove(0, 0);
    Assert.assertTrue(isValid);
  }

  /**
   * Test isValidMove with an invalid move (cell already occupied).
   */
  @Test
  public void testIsValidMoveInvalidOccupiedCell() {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    Card cardToPlace = currentPlayer.getPlayerHand().get(0);
    model.placeCard(0, 0, cardToPlace);
    Assert.assertFalse(model.isValidMove(0, 0));
  }

  /**
   * Test isValidMove with coordinates outside the grid boundaries.
   */
  @Test
  public void testIsValidMoveOutOfBounds() {
    Assert.assertFalse(model.isValidMove(0, -1));
    Assert.assertFalse(model.isValidMove(-1, 0));
    Assert.assertFalse(model.isValidMove(grid.getRows(), 0));
    Assert.assertFalse(model.isValidMove(0, grid.getCols()));
  }

  /**
   * Test maxCombo returns correct maximum flips.
   */
  @Test
  public void testMaxCombo() {
    GamePlayer redPlayer = model.getPlayers().get(0);
    GamePlayer bluePlayer = model.getPlayers().get(1);

    Card redCard = redPlayer.getPlayerHand().get(0);
    model.placeCard(0, 0, redCard);

    Card blueCard = bluePlayer.getPlayerHand().get(1);
    int expectedFlips = 0;
    int maxFlips = model.maxCombo(blueCard, 0, 1);
    Assert.assertEquals(expectedFlips, maxFlips);

    model.placeCard(0, 1, blueCard);

    GamePlayer owner = model.getCellOwner(0, 0);
    Assert.assertEquals(redPlayer, owner);
  }


  /**
   * Test getPlayerScore includes cards on the grid and in hand.
   */
  @Test
  public void testGetPlayerScore() throws IOException {
    ThreeTriosView view = new ThreeTriosGameView(model, System.out);
    GamePlayer redPlayer = model.getPlayers().get(0);
    GamePlayer bluePlayer = model.getPlayers().get(1);
    int redScore = model.getPlayerScore(redPlayer);
    int blueScore = model.getPlayerScore(bluePlayer);
    Assert.assertEquals(redScore, blueScore); // equal at start.

    model.placeCard(0, 0, redPlayer.getPlayerHand().get(0));
    view.display();

    redScore = model.getPlayerScore(redPlayer);
    blueScore = model.getPlayerScore(bluePlayer);
    // Red's score should be equal (red hand changed, but still same cards owned).
    Assert.assertEquals(redScore, blueScore);
  }

  /**
   * Test that simulateFlips correctly counts the number of flips (via maxCombo).
   */
  @Test
  public void testSimulateFlipsViaMaxCombo() throws IOException {
    ThreeTriosView view = new ThreeTriosGameView(model, System.out);

    GamePlayer redPlayer = model.getPlayers().get(0);
    GamePlayer bluePlayer = model.getPlayers().get(1);

    Card redCard1 = redPlayer.getPlayerHand().get(0);
    model.placeCard(0, 0, redCard1);
    view.display();
    // Turn switches to BLUE

    Card blueCard = bluePlayer.getPlayerHand().get(0);
    model.placeCard(1, 0, blueCard);
    view.display();
    Card redCard2 = redPlayer.getPlayerHand().get(0);

    int expectedFlips = model.maxCombo(redCard2, 2, 0);

    Assert.assertEquals(1, expectedFlips);

    model.placeCard(2, 0, redCard2);
    view.display();

    GamePlayer owner = model.getCellOwner(1, 0);
    Assert.assertEquals(redPlayer, owner);
  }

  /**
   * Test placing a card in a hole cell (should throw an exception).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardInHoleCell() {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    Card cardToPlace = currentPlayer.getPlayerHand().get(0);

    Cell cell = grid.getCell(1, 1);

    model.placeCard(1, 1, cardToPlace);
  }

  /**
   * Test attempting to place a card not in the player's hand.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardNotInHand() {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    Card invalidCard = new GameCard("wasd", 1, 2, 3, 4); // Card not in hand

    model.placeCard(0, 0, invalidCard);
  }

  /**
   * Test attempting to place a card when the player has no cards.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardWhenHandIsEmpty() {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    currentPlayer.getPlayerHand().clear(); // Empty the player's hand

    Card cardToPlace = new GameCard("wasd", 1, 2, 3, 4); // Any card

    model.placeCard(0, 0, cardToPlace);
  }

  /**
   * Test getOppositeDirection method indirectly via battleCells.
   */
  @Test
  public void testBattleCellsOppositeDirection() throws IOException {
    ThreeTriosGameView view = new ThreeTriosGameView(model, System.out);

    GamePlayer redPlayer = model.getPlayers().get(0);
    Card redCard = redPlayer.getPlayerHand().get(0);
    model.placeCard(0, 0, redCard);
    view.display();

    GamePlayer bluePlayer = model.getPlayers().get(1);
    Card blueCard = bluePlayer.getPlayerHand().get(0);

    model.placeCard(1, 0, blueCard);
    view.display();
    GamePlayer owner = model.getCellOwner(0, 0);
    Assert.assertEquals(redPlayer, owner);
  }

  /**
   * Test that the game does not start until startGame is called.
   */
  @Test(expected = IllegalStateException.class)
  public void testGameNotStarted() {
    // Create a new model but do not start the game
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    List<Card> newDeck = cardConfigReader.readCards("HW5" + File.separator + "src"
            + File.separator + "resources" + File.separator + "CardDb.txt");
    Grid newGrid = gridConfigReader.readGridFromFile("HW5" + File.separator + "src"
            + File.separator + "resources" + File.separator + "GridDb.txt");

    PlayerFactory newPlayerFactory = new HumanPlayerFactory();

    GameModel newModel = new ThreeTriosModel(newGrid, newPlayerFactory, newDeck);

    // Attempt to place a card without starting the game
    GamePlayer currentPlayer = newModel.getCurrentPlayer();
    Card cardToPlace = new GameCard("232", 1, 2, 3, 4); // Any card
    newModel.placeCard(0, 0, cardToPlace);
  }

  /**
   * Test that getWinner returns the correct player when one has more cards in hand.
   */
  @Test
  public void testGetWinnerBasedOnHandSize() {
    GamePlayer redPlayer = model.getPlayers().get(0);
    GamePlayer bluePlayer = model.getPlayers().get(1);

    // Remove all cards from blue player's hand
    bluePlayer.getPlayerHand().clear();

    // Place cards to fill the grid
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        if (!grid.getCell(row, col).isHole()) {
          if (model.getCurrentPlayer() == redPlayer && !redPlayer.getPlayerHand().isEmpty()) {
            model.placeCard(row, col, redPlayer.getPlayerHand().get(0));
          } else if (model.getCurrentPlayer()
                  == bluePlayer && !bluePlayer.getPlayerHand().isEmpty()) {
            model.placeCard(row, col, bluePlayer.getPlayerHand().get(0));
          } else {
            model.switchTurn();
          }
        }
      }
    }

    // Now redPlayer should have more cards (in hand)
    GamePlayer winner = model.getWinner();
    Assert.assertEquals(redPlayer, winner);
  }
}
