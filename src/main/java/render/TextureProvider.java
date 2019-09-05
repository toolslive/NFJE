package render;

import java.util.*;

public class TextureProvider {
  public static TextureProvider getInstance() {
    if (_instance == null) {
      new TextureProvider();
    }
    return _instance;
  }

  private static TextureProvider _instance;

  public Texture getTexture(int number) {
    return _textures.elementAt(number);
  }

  protected Vector<Texture> _textures = new Vector<Texture>();

  protected void _init() {
    TextureFactory factory = new TextureFactory();
    try {
      Texture current = factory.loadTexture("data/texture.jpg");
      _textures.addElement(current);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected TextureProvider() {
    _instance = this;
    _init();
  }
}
