package render;

import java.util.Vector;
import util.Point2;

public class Point2L {
  Vector<Point2> _points = new Vector<Point2>();

  public Point2L() {}

  public void add(Point2 point) {
    _points.addElement(point);
  }

  public int size() {
    return _points.size();
  }

  public Point2 getPoint(int index) {
    Point2 result = _points.elementAt(index);
    return result;
  }

  public String toString() {
    return _points.toString();
  }
}
