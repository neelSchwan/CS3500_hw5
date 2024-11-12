package cs3500.threetrios.controller;

import java.util.Scanner;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;

public class ThreeTriosConsoleController implements ThreeTriosController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructs a TicTacToeConsoleController with the provided input and output.
   *
   * @param in  the Readable source of input.
   * @param out the Appendable destination for output.
   * @throws IllegalArgumentException if either input or output is null.
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
