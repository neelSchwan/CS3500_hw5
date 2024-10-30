package cs3500.threetrios.model;

/**
 * Class that represents the player in the three-trios game, which is represented by either.
 * Player.RED or Player.BLUE.
 * In the current implementation, we use an enum 'Player' to represent a RED or BLUE player.
 * we know that this tightly couples the players logic to the model, and will be changing the logic
 * for the next assignment so that a Player implements the 'GamePlayer' interface.
 */
public enum Player {
  RED, BLUE
}
