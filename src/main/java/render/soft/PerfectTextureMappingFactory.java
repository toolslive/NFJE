package render.soft;

import render.*;

public class PerfectTextureMappingFactory extends TextureMappingFactory {
  public PerfectTextureMappingFactory() {
    super();
  }

  public TextureMapping createTextureMapping() {
    return new PerfectTextureMapping();
  }
}
