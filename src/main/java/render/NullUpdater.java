/*
 * NullUpdater.java
 *
 */

package render;

/** @author EvilSloot */
public class NullUpdater implements Updater {
  private NullUpdater() {}

  public void update(RenderableProvider x) {}

  public static final Updater NULL_UPDATER = new NullUpdater();
}
