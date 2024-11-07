package cs3500.threetrios.controller;

import cs3500.threetrios.model.GameModel;

public interface ThreeTriosController {
  void playGame(GameModel model);

  void handleCellClick(int row, int col);
}
