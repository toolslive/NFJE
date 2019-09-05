package render.soft;

import render.*;
import util.*;

public class PolygonCanonizer extends PolygonStage {
  private SoftEngine _engine;

  public PolygonCanonizer(PolygonStage next) {
    super(next);
    _engine = (SoftEngine) Engine.getEngine();
  }

  protected String _toString() {
    return "Canonizer";
  }

  protected Polygon _process(Polygon in) {
    int numberOfPoints = in.numberOfPoints();
    Point3L points = new Point3L();

    int[] indices = new int[numberOfPoints];
    Point3 current;
    Point3 transformed;
    for (int i = 0; i < numberOfPoints; i++) {
      current = in.getPoint(i);
      transformed = _engine.toCanonical(current);
      points.add(transformed);
      indices[i] = i;
    }
    PolygonImpl out = new PolygonImpl(indices, points, in.getTextureCoordinates());
    out.setTexture(in.getTexture());
    return out;
  }
}
