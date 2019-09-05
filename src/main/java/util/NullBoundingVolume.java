package util;

public class NullBoundingVolume implements BoundingVolume {
  public Point3 getIntersection(Point3 eye, Point3 dir) {
    return eye;
  }

  public Point3 getCenterPoint() {
    return null;
  }

  public int intersection(BoundingVolume v) {
    return DONT_KNOW;
  }
}
