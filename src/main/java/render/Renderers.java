package render;

import java.util.*;

public class Renderers {

  private static Renderers _instance = new Renderers();
  private Hashtable<String, Renderer> _hash = new Hashtable<String, Renderer>();

  public static Renderers getInstance() {
    return _instance;
  }

  public static void register(Renderer renderer) {
    String type = renderer.type();
    _instance.register(renderer, type);
  }

  public void register(Renderer renderer, String type) {
    System.out.println(type + " -> " + renderer);
    _hash.put(type, renderer);
  }

  public Renderer getRenderer(String type) {
    Renderer result = _hash.get(type);
    if (result == null) {
      result = NullRenderer.NULL_RENDERER;
    }
    return result;
  }

  public void render(Renderable r) {
    String type = r.type();
    Renderer renderer = getRenderer(type);
    renderer.render(r);
  }
}
