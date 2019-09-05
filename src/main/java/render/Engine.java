package render;

import java.awt.*;
import java.util.Enumeration;
import stats.Statistics;
import util.*;

public abstract class Engine {

  public abstract void pushTransformation(Matrix44 m);

  public abstract Matrix44 popTransformation();
  /** number of objects that we ignored for rendering. */
  protected int _dontRender = 0;

  protected int _render = 0;

  protected Renderers _renderers = Renderers.getInstance();
  protected Frustum _frustum;
  /**
   * render the renderable from a single Provider. uses the renderable's bounding volume to see
   * wether it's inside the Frustum or not.
   */
  protected void renderProvider(RenderableProvider provider) {
    Renderable renderable = provider.getRenderable();
    RenderableProvider child;
    if (_mustRender(renderable)) {
      String type = renderable.type();
      Renderer renderer = _renderers.getRenderer(type);
      renderer.render(renderable);
      Enumeration children = provider.getChildren();
      while (children.hasMoreElements()) {
        child = (RenderableProvider) children.nextElement();
        renderProvider(child);
      }
    }
  }

  protected boolean _mustRender(Renderable renderable) {
    BoundingVolume volume = renderable.getBoundingVolume();
    int intersection = _frustum.intersection(volume);
    if (intersection == BoundingVolume.OUTSIDE) {
      _dontRender++;
      return false;
    }
    _render++;
    return true;
  }

  /** */
  public void renderProviders(Enumeration providers) {
    RenderableProvider provider;
    Matrix44 transformation;
    VisualWorld world = VisualWorld.getVisualWorld();
    Camera camera = world.getCamera();
    _frustum = new Frustum(camera);

    while (providers.hasMoreElements()) {
      provider = (RenderableProvider) providers.nextElement();
      transformation = provider.getTransformation();
      pushTransformation(transformation);
      renderProvider(provider);
      popTransformation();
    }
  }

  public void newFrame() {

    Statistics.update("render", _render + "/" + _dontRender);
    Statistics.update("Point3()", "" + Point3.constructed);
    Statistics.update("Point4()", "" + Point4.constructed);
    Statistics.update("PolygonImpl()", "" + PolygonImpl.constructed);
    Statistics.update("Sphere()", "" + Sphere.constructed);

    Sphere.constructed = 0L;
    Point3.constructed = 0L;
    Point4.constructed = 0L;
    PolygonImpl.constructed = 0L;
    _render = 0;
    _dontRender = 0;
  }

  public Engine() {
    _instance = this;
  }

  private static Engine _instance;

  /**
   * static accessor method. don't know wether we want to be able to change the engine at runtime or
   * not... if that's the case, no object should be allowed to cash the reference.
   */
  public static Engine getEngine() {
    return _instance;
  }
}
