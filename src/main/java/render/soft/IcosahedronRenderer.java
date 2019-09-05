package render.soft;

import render.*;
import twod.*;
import util.*;

/** */
public class IcosahedronRenderer implements Renderer {

  private SoftEngine _engine;

  public IcosahedronRenderer(Engine engine) {
    Renderers.register(this);
    _engine = (SoftEngine) engine;
  }

  public String type() {
    return "Icosahedron";
  }

  public void render(Renderable r) {
    Icosahedron i = (Icosahedron) r;
    renderIcosahedron(i);
  }

  public String toString() {
    String result = "Software IcosahedronRenderer " + "(uses default renderer for Polygon type)";
    return result;
  }

  public void renderIcosahedron(Icosahedron icos) {
    System.out.print('.');
    int numberOfPolygons = icos.polys.length;
    // backface culling shouldn't use camera position
    // but the last matrix on the transformation stack...
    //
    for (int poly = 0; poly < numberOfPolygons; poly++) {
      Polygon polygon = icos.polys[poly];
      _renderPolygon(polygon);
    }
  }

  protected void _renderPolygon(Polygon polygon) {
    Renderers renderers = Renderers.getInstance();
    renderers.render(polygon);
  }
}
