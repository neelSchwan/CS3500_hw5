package cs3500.threetrios.provider.model;

/**
 * Features for the model.
 */
public interface ModelFeatures {

  /**
   * Adds a model listener (subscriber).
   * @param listener the listener
   */
  void addModelListener(ModelListener listener);

  /**
   * Removes a model listener.
   * @param listener the listener
   */
  void removeModelListener(ModelListener listener);
}
