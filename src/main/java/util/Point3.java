package util;

public class Point3 {
  public float[] _p;
  public static long constructed = 0;

  public Point3() {
    constructed++;
    _p = new float[3];
  }

  public Point3(float x, float y, float z) {
    this();
    _p[0] = x;
    _p[1] = y;
    _p[2] = z;
  }

  public Point3(float[] flat) {
    this();
    for (int i = 0; i < 3; i++) {
      _p[i] = flat[i];
    }
  }

  public Point3(Point3 p) {
    this();
    copy(p);
  }

  public void copy(Point3 p) {
    for (int i = 0; i < 3; i++) {
      _p[i] = p._p[i];
    }
  }

  public void scale(float f) {
    for (int i = 0; i < 3; i++) {
      _p[i] *= f;
    }
  }

  public Point3 scaled(float f) {
    return new Point3(_p[0] * f, _p[1] * f, _p[2] * f);
  }

  public Point3 add(Point3 p2) {
    Point3 result = new Point3(_p[0] + p2._p[0], _p[1] + p2._p[1], _p[2] + p2._p[2]);
    return result;
  }

  public Point3 sub(Point3 p2) {
    Point3 result = new Point3(_p[0] - p2._p[0], _p[1] - p2._p[1], _p[2] - p2._p[2]);
    return result;
  }

  public Point3 minus() {
    Point3 result = new Point3(-_p[0], -_p[1], -_p[2]);
    return result;
  }

  public float dot(Point3 p2) {
    float result = _p[0] * p2._p[0] + _p[1] * p2._p[1] + _p[2] * p2._p[2];
    return result;
  }

  public Point3 block(Point3 p2) {
    float x = _p[1] * p2._p[2] - _p[2] * p2._p[1];
    float y = _p[2] * p2._p[0] - _p[0] * p2._p[2];
    float z = _p[0] * p2._p[1] - _p[1] * p2._p[0];
    return new Point3(x, y, z);
  }

  public Point3 cross(Point3 p2) {
    float x = _p[1] * p2._p[2] - _p[2] * p2._p[1];
    float y = _p[2] * p2._p[0] - _p[0] * p2._p[2];
    float z = _p[0] * p2._p[1] - _p[1] * p2._p[0];
    return new Point3(x, y, z);
  }

  public float length() {
    float l2 = dot(this);
    return (float) Math.sqrt(l2);
  }

  public void normalize() {
    float l = length();
    scale(1.0f / l);
  }

  public static Point3 addition(Point3 p1, Point3 p2) {
    return new Point3(p1._p[0] + p2._p[0], p1._p[1] + p2._p[1], p1._p[2] + p2._p[2]);
  }
  /**
   * addition without object creation.
   *
   * @param p1
   * @param p1
   * @param out where to store the result.
   */
  public static void addition(Point3 p1, Point3 p2, Point3 out) {
    out._p[0] = p1._p[0] + p2._p[0];
    out._p[1] = p1._p[1] + p2._p[1];
    out._p[2] = p1._p[2] + p2._p[2];
  }
  /**
   * substraction without object creation.
   *
   * @param p1
   * @param p1
   * @param out where to store the result.
   */
  public static void substraction(Point3 p1, Point3 p2, Point3 out) {
    out._p[0] = p1._p[0] - p2._p[0];
    out._p[1] = p1._p[1] - p2._p[1];
    out._p[2] = p1._p[2] - p2._p[2];
  }

  public static Point3 substraction(Point3 p1, Point3 p2) {
    return new Point3(p1._p[0] - p2._p[0], p1._p[1] - p2._p[1], p1._p[2] - p2._p[2]);
  }

  public static void rotateY(Point3 in, Point3 out, float angle) {
    float cos = (float) Math.cos(angle);
    float sin = (float) Math.sin(angle);
    float nx = in._p[0] * cos + in._p[2] * sin;
    float nz = in._p[2] * cos - in._p[0] * sin;
    out._p[0] = nx;
    out._p[1] = in._p[1];
    out._p[2] = nz;
  }
  /** */
  public static boolean equals(Point3 v, Point3 w, float eps) {
    float dx, dy, dz;
    dx = w._p[0] - v._p[0];
    dy = w._p[1] - v._p[1];
    dz = w._p[2] - v._p[2];
    boolean equals = false;
    if (Math.abs(dx) < eps && Math.abs(dy) < eps && Math.abs(dz) < eps) {
      equals = true;
    }
    return equals;
  }

  public String f(float d) {
    String result = "" + d;
    return result;
  }

  public String toString() {
    return "(" + f(_p[0]) + "," + f(_p[1]) + "," + f(_p[2]) + ")";
  }
}
