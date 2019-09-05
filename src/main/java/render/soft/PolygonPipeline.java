package render.soft;

import render.*;

public class PolygonPipeline extends PolygonStage implements Renderer {
  public String type() {
    return "Polygon";
  }

  public String toString() {
    return _toString() + "(" + _next + ")";
  }

  protected String _toString() {
    return "Pipeline";
  }

  PolygonPipeline() {
    super(null);
    PolygonRasterer rasterer = new PolygonRasterer();
    PolygonSorter sorter = new PolygonSorter(rasterer);
    PolygonClipper clipper = new PolygonClipper(sorter);
    PolygonCanonizer canonizer = new PolygonCanonizer(clipper);
    _next = canonizer;
    Renderers.register(this);
  }

  public void render(Renderable r) {
    Polygon p = (Polygon) r;
    accept(p);
  }
}
