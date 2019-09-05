package render;

import java.util.Vector;
import util.Point3;

public class Point3L {
  Vector<Point3> _points = new Vector<Point3>();
  // Point3 _base=new Point3();
  public Point3L() {
    // _base=new Point3();
  }

  public void add(Point3 point) {
    _points.addElement(point);
  }

  public int size() {
    return _points.size();
  }

  public Point3 getPoint(int index) {
    Point3 result = _points.elementAt(index);
    return result;
  }

  public String toString() {
    return _points.toString();
  }
}
