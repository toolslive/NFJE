package render;

public abstract class TextureMappingFactory {
  public static TextureMappingFactory getInstance() {
    return _instance;
  }

  private static TextureMappingFactory _instance;

  public TextureMappingFactory() {
    _instance = this;
  }

  public abstract TextureMapping createTextureMapping();
}
