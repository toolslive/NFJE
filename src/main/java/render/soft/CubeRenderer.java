package render.soft;

import render.*;
import twod.*;
import util.*;

/** */
public class CubeRenderer implements Renderer {
  SoftEngine _engine;

  public CubeRenderer(Engine e) {
    Renderers.register(this);
    _engine = (SoftEngine) e;
  }

  public String type() {
    return "Cube";
  }

  public void render(Renderable r) {
    Cube c = (Cube) r;
    renderCube(c);
  }

  public String toString() {
    String result = "Software CubeRenderer (uses default renderer for Polygon type)";
    return result;
  }

  public void renderCube(Cube cube) {
    int numberOfPolygons = cube.polys.length;
    Camera c = VisualWorld.getVisualWorld().getCamera();

    Point3 direction = c.direction();

    for (int poly = 0; poly < numberOfPolygons; poly++) {
      Polygon polygon = cube.polys[poly];
      Point4 normal = polygon.getNormal();
      normal = _engine.toCanonical(normal);
      Point3 g = polygon.getG();
      g = _engine.toCanonical(g);
      // backface culling:
      Point3 n3 = normal.toPoint3();
      double dot = n3.dot(g);
      if (dot > 0.0) {
        // if you want hidden line removal:

        // Renderers renderers=Renderers.getInstance();
        // renderers.render(polygon);

        _renderPolygon(polygon);
      }
    }
  }

  protected void _renderPolygon(Polygon polygon) {
    Renderers renderers = Renderers.getInstance();
    renderers.render(polygon);
  }
}
