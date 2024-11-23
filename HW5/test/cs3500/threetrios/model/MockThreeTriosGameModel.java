package cs3500.threetrios.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock for the three trios model, this is used for testing the strategies and the controller.
 */
public class MockThreeTriosGameModel implements GameModel {
  private final StringBuilder log;
  private final GamePlayer currentPlayer;
  private final Grid grid;
  private final int maxComboReturn;
  private final List<GamePlayer> players;

  /**
   * Constructs a mock implementation of the ThreeTrios game model for testing.
   *
   * @param log            log to record method calls and actions for testing.
   * @param currentPlayer  GamePlayer representing the current player in the mock game.
   * @param grid           Grid representing the game board for the mock model.
   * @param maxComboReturn the predefined value to return when the maxCombo method is called.
   */
  public MockThreeTriosGameModel(StringBuilder log,
                                 GamePlayer currentPlayer, Grid grid, int maxComboReturn) {
    this.log = log;
    this.currentPlayer = currentPlayer;
    this.grid = grid;
    this.maxComboReturn = maxComboReturn;
    this.players = new ArrayList<>();
    players.add(currentPlayer);
    players.add(new HumanPlayer(Player.BLUE, new ArrayList<>()));
  }

  @Override
  public int gridSize() {
    return grid.getRows() + grid.getCols();
  }

  @Override
  public Card cellContents(int row, int col) {
    log.append(String.format("Checking cell contents at (%d,%d)%n", row, col));
    return null;
  }

  @Override
  public GamePlayer getCellOwner(int row, int col) {
    log.append(String.format("Checking cell owner at (%d,%d)%n", row, col));
    return null;
  }

  @Override
  public boolean isValidMove(int row, int col) {
    log.append(String.format("Checking move validity at (%d,%d)%n", row, col));
    return row >= 0 && row < grid.getRows() && col >= 0 && col < grid.getCols();
  }

  @Override
  public int maxCombo(Card card, int row, int col) {
    log.append(String.format("Checking maxCombo for %s at (%d,%d)%n", card.getName(), row, col));
    return maxComboReturn;
  }

  @Override
  public int getPlayerScore(GamePlayer player) {
    log.append(String.format("Getting score for player %s%n", player.getColor()));
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Grid getGrid() {
    return grid;
  }

  @Override
  public GamePlayer getCurrentPlayer() {
    log.append("Getting current player\n");
    return currentPlayer;
  }

  @Override
  public GamePlayer getWinner() {
    log.append("Checking winner\n");
    return null;
  }

  @Override
  public List<GamePlayer> getPlayers() {
    return new ArrayList<>(players);
  }

  public String getLog() {
    return log.toString();
  }

  /**
   * Adds a player to the final list of players, the first player will be the RED, and second BLUE.
   *
   * @param player player to add.
   */
  @Override
  public void addPlayer(GamePlayer player) {
    log.append(String.format("Adding player %s%n", player.getColor()));
  }

  /**
   * Places a card in the grid at the specified row and column if the cell is a card-cell.
   *
   * @param row  the row index of the cell (0-indexed).
   * @param col  the column index of the cell (0-indexed).
   * @param card the {@link Card} object to place in the grid.
   */
  @Override
  public void placeCard(int row, int col, Card card) {
    log.append(String.format("Placing card %s at (%d,%d)%n", card.getName(), row, col));
  }

  /**
   * Switches to the next player's turn. If the current player is RED,
   * switches to BLUE, and vice versa.
   */
  @Override
  public void switchTurn() {
    log.append("Switching turn\n");
  }

  /**
   * Starts the game by shuffling the deck and dealing cards to the players.
   *
   * @param seed the random seed used to shuffle the deck for reproducibility.
   * @throws IllegalStateException if there are not enough cards in the deck to start the game.
   */
  @Override
  public void startGame(long seed) {
    log.append("Starting game with seed: " + seed + "\n");
  }

  /**
   * Initializes the game grid and player hands based on a configuration file.
   *
   * @param gridFile the file containing the grid configuration.
   * @param cardFile the file containing the card configuration.
   * @throws IllegalArgumentException if the configuration files are invalid.
   */
  @Override
  public void initializeGame(String gridFile, String cardFile) {
    log.append(String.format("Initializing game with files: %s, %s%n", gridFile, cardFile));
  }

  /**
   * Adds a listener for model events.
   *
   * @param listener the listener to add
   */
  @Override
  public void addGameModelListener(GameModelListener listener) {
    log.append("Adding game model listener\n");
  }
}