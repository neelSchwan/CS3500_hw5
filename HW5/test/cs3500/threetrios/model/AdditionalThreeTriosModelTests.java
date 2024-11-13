package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cs3500.threetrios.view.ThreeTriosGameGUIView;
import cs3500.threetrios.view.ThreeTriosGamePanel;
import cs3500.threetrios.view.ThreeTriosGameView;
import cs3500.threetrios.view.ThreeTriosView;

/**
 * Additional test cases for the ThreeTriosModel class.
 */
public class AdditionalThreeTriosModelTests {

  private GameModel model;
  private List<Card> deck;
  private Grid grid;
  private PlayerFactory playerFactory;

  /**
   * Initializes the test setup with a game model, deck of cards, grid, and player hands.
   * This method runs before each test to ensure consistency between model tests.
   */
  @Before
  public void setUp() {
    CardConfigReader cardConfigReader = new CardConfigReader();
    GridConfigReader gridConfigReader = new GridConfigReader();

    deck = cardConfigReader.readCards("src" + File.separator
            + "resources" + File.separator + "CardDb.txt");
    grid = gridConfigReader.readGridFromFile("src" + File.separator
            + "resources" + File.separator + "GridDb.txt");

    playerFactory = new HumanPlayerFactory();

    model = new ThreeTriosModel(grid, playerFactory, deck);
    model.startGame(0);
  }

  /**
   * Test that getGrid returns a deep copy of the grid.
   */
  @Test
  public void testGetGridReturnsDeepCopy() {
    Grid originalGrid = model.getGrid();
    Grid gridCopy = model.getGrid();

    // Modify the copy
    gridCopy.setCell(0, 0, new Cell(CellType.HOLE_CELL));

    // Verify that the original grid is not affected
    Assert.assertNotEquals(originalGrid.getCell(0, 0).isHole(), gridCopy.getCell(0, 0).isHole());
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
    GamePlayer currentPlayer = model.getCurrentPlayer();
    Card cardToPlace = currentPlayer.getPlayerHand().get(0);
    model.placeCard(0, 0, cardToPlace);

    // Switch to the next player
    model.switchTurn();
    currentPlayer = model.getCurrentPlayer();
    Card opponentCard = currentPlayer.getPlayerHand().get(0);

    int maxFlips = model.maxCombo(opponentCard, 0, 1);
    Assert.assertTrue(maxFlips >= 0);

    // Place the card and verify flips
    model.placeCard(0, 1, opponentCard);
    GamePlayer owner = model.getCellOwner(0, 0);
    Assert.assertEquals(currentPlayer, owner);
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
    // Update scores
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
    ThreeTriosGameView view = new ThreeTriosGameView(model, System.out);
    GamePlayer redPlayer = model.getPlayers().get(0);
    GamePlayer bluePlayer = model.getPlayers().get(1);
    view.display();
    // Red places a card
    model.placeCard(0, 0, redPlayer.getPlayerHand().get(0));
    view.display();
    // Switch to blue and test maxCombo
    Card blueCard = bluePlayer.getPlayerHand().get(0);
    System.out.println(blueCard);
    int expectedFlips = model.maxCombo(blueCard, 1, 0);

    // Place the card and verify actual flips
    model.placeCard(1, 1, blueCard);

    // Since battlePhase has been executed, verify that the number of flips matches expectedFlips
    GamePlayer owner = model.getCellOwner(0, 0);
    if (expectedFlips > 0) {
      Assert.assertEquals(bluePlayer, owner);
    } else {
      Assert.assertEquals(redPlayer, owner);
    }
  }

  /**
   * Test placing a card in a hole cell (should throw an exception).
   */
  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCardInHoleCell() {
    GamePlayer currentPlayer = model.getCurrentPlayer();
    Card cardToPlace = currentPlayer.getPlayerHand().get(0);

    // Assuming (1,1) is a hole in the grid
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

    List<Card> newDeck = cardConfigReader.readCards("src" + File.separator
            + "resources" + File.separator + "CardDb.txt");
    Grid newGrid = gridConfigReader.readGridFromFile("src" + File.separator
            + "resources" + File.separator + "GridDb.txt");

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
          } else if (model.getCurrentPlayer() == bluePlayer && !bluePlayer.getPlayerHand().isEmpty()) {
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
