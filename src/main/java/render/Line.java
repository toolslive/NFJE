package render;

import util.*;

public class Line implements Renderable {
  public Point3 _p0;
  public Point3 _p1;

  public Line() {}

  public Matrix44 getTransformation() {
    return Matrix44.createUnit();
  }

  public Line(Point3 p0, Point3 p1) {
    _p0 = p0;
    _p1 = p1;
  }

  public String type() {
    return "Line";
  }

  public String toString() {
    return "" + _p0 + "," + _p1;
  }

  public BoundingVolume getBoundingVolume() {
    return BoundingVolume.NULL;
  }
}
