package cs3500.threetrios.controller;

import java.util.Scanner;

import cs3500.threetrios.model.GameModel;

public class ThreeTriosConsoleController implements ThreeTriosController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructs a {@code TicTacToeConsoleController} with the provided input and output.
   *
   * @param in  the {@link Readable} source of input (e.g., user commands)
   * @param out the {@link Appendable} destination for output (e.g., game state and prompts)
   * @throws IllegalArgumentException if either {@code in} or {@code out} is {@code null}
   */
  public ThreeTriosConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(GameModel model) {

  }

  @Override
  public void handleCellClick(int row, int col) {

  }
}
