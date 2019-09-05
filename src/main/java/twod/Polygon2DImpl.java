package twod;

import java.util.*;

public class Polygon2DImpl implements Polygon2D {
  private Vector _points;

  public Polygon2DImpl(Vector points) {
    _points = points;
  }

  public int numberOfPoints() {
    return _points.size();
  }

  public Point2 getPoint(int pointNr) {
    return (Point2) _points.elementAt(pointNr);
  }
}
