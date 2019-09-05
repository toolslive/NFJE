package render;

import java.util.Hashtable;

public class Updaters {
  private static Updaters _instance = new Updaters();
  private Hashtable<RenderableProvider, Updater> _hash =
      new Hashtable<RenderableProvider, Updater>();

  public static Updaters getInstance() {
    return _instance;
  }

  public Updater getUpdater(RenderableProvider provider) {

    Updater result = _hash.get(provider);
    if (result == null) {
      result = NullUpdater.NULL_UPDATER;
    }
    return result;
  }

  public void register(RenderableProvider provider, Updater updater) {
    _hash.put(provider, updater);
  }

  public static void update(RenderableProvider provider) {
    Updaters instance = Updaters.getInstance();
    Updater updater = instance.getUpdater(provider);
    updater.update(provider);
  }
}
