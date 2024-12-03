package cs3500.threetrios.provider.view;

/**
 * Interface for adding and removing view listeners.
 */
public interface ViewFeatures {

  /**
   * Adds a view listener.
   *
   * @param listener listener
   */
  void addViewListener(ViewListener listener);

  /**
   * Removes a view listener.
   *
   * @param listener listener
   */
  void removeViewListener(ViewListener listener);
}
