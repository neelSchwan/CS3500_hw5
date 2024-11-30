package cs3500.view;

/**
 * The listener for the board panel so it knows how to respond to
 * game actions.
 */
public interface BoardPanelListener {
  /**
   * How the board will react to the position selected.
   *
   * @param row row of position
   * @param col col of position
   */
  void positionSelected(int row, int col);
}
