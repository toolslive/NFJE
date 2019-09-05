package render.soft;

import render.*;
import util.*;

public class PolygonMeshRenderer implements Renderer {
  public PolygonMeshRenderer() {
    Renderers.register(this);
  }

  public String type() {
    return "PolygonMesh";
  }

  public void render(Renderable r) {
    PolygonMesh mesh = (PolygonMesh) r;
    renderMesh(mesh);
  }

  public String toString() {
    String result = "Software PolygonMeshRenderer";
    return result;
  }

  public void renderMesh(PolygonMesh mesh) {
    int numberOfPolygons = mesh.size();
    Camera c = VisualWorld.getVisualWorld().getCamera();
    Point3 direction = c.direction();
    Renderers renderers = Renderers.getInstance();
    for (int poly = 0; poly < numberOfPolygons; poly++) {
      Polygon polygon = mesh.getPolygon(poly);
      renderers.render(polygon);
    }
  }
}
