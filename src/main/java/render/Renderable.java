package render;

import util.BoundingVolume;

public interface Renderable {
  public String type();

  public BoundingVolume getBoundingVolume();
}
