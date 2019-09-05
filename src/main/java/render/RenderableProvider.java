package render;

import java.util.*;
import util.Matrix44;

public interface RenderableProvider {
  public Renderable getRenderable();

  public Matrix44 getTransformation();
  /** returns the enumeration of the children, and a NullEnumeration if there are none. */
  public Enumeration getChildren();
}
