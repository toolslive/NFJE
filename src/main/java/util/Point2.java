package util;

public class Point2 {
  public float[] _p;
  public static long constructed = 0;

  public Point2() {
    constructed++;
    _p = new float[2];
  }

  public Point2(float x, float y) {
    this();
    _p[0] = x;
    _p[1] = y;
  }

  public String f(float d) {
    String result = "" + d;
    return result;
  }

  public String toString() {
    return "(" + f(_p[0]) + "," + f(_p[1]) + ")";
  }

  public static boolean equals(Point2 v, Point2 w, float eps) {
    float dx, dy;
    dx = w._p[0] - v._p[0];
    dy = w._p[1] - v._p[1];
    boolean equals = false;
    if (Math.abs(dx) < eps && Math.abs(dy) < eps) {
      equals = true;
    }
    return equals;
  }
}
