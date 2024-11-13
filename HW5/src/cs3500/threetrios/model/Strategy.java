package cs3500.threetrios.model;

import java.util.List;

public interface Strategy {
  Move determineMove(GameModel gameState, List<Card> hand);
}