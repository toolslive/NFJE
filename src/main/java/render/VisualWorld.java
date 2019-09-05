package render;

import java.util.*;
import util.*;

public class VisualWorld {
  private static VisualWorld _instance = new VisualWorld();
  protected Vector<RenderableProvider> _renderableProviders;
  protected Camera _camera = new Camera();

  public static VisualWorld getVisualWorld() {
    return _instance;
  }

  protected VisualWorld() {
    _renderableProviders = new Vector<RenderableProvider>();
  }

  public static void update() {
    VisualWorld world = getVisualWorld();
    Enumeration<RenderableProvider> providers = world.renderableProviders();
    RenderableProvider provider;
    Updater updater;
    while (providers.hasMoreElements()) {
      provider = providers.nextElement();
      Updaters.update(provider);
    }
  }

  public static void register(RenderableProvider provider) {
    getVisualWorld()._renderableProviders.addElement(provider);
  }

  public static void clean() {
    _instance = new VisualWorld();
    System.out.println("cleaned");
  }
  /** an iterator of renderableProviders */
  public Enumeration<RenderableProvider> renderableProviders() {
    return _renderableProviders.elements();
  }

  public Camera getCamera() {
    return _camera;
  }
}
