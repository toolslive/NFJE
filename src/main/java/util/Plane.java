package util;

public class Plane {
  Point4 _normal;
  private static final float EPS = 1.0e-6f;

  /** equation is np=0 with n a 4x1 matrix, so p has to be in homogeneous coordinates. */
  public Plane(Point4 normal) {
    _setup(normal);
  }

  /** @return the distance from the point to the plane. */
  public float distance(Point3 p) {
    return dot(p) + _normal._p[3];
  }

  public float dot(Point3 p) {
    return _normal._p[0] * p._p[0] + _normal._p[1] * p._p[1] + _normal._p[2] * p._p[2];
  }

  /** @return intersection with the line defined by these 2 points , null if parallel */
  public Point3 intersection(Point3 p0, Point3 p1) {
    Point3 dir = Point3.substraction(p1, p0);
    float denom = dot(dir);
    float nominator = -(dot(p0) + _normal._p[3]);
    if (denom < EPS && -EPS < denom) {
      return null;
    }

    float t = nominator / denom;
    Point3 result = new Point3(p0);
    result._p[0] += t * dir._p[0];
    result._p[1] += t * dir._p[1];
    result._p[2] += t * dir._p[2];
    return result;
  }
  /**
   * applies a transformation on the plane. (Basically applies the transpose of the inverse to the
   * normal). If you want to use the distance function, be sure that you only use Euclidian
   * transformations
   */
  public void applyTransformation(Matrix44 t) {
    Matrix44 inverse = t.affineInverse();
    Matrix44 transpose = inverse.transpose();
    Point4 newNormal = transpose.applyOn(_normal);
    _setup(newNormal); // needed ?
  }

  private void _setup(Point4 normal) {

    Point3 n = new Point3(normal._p[0], normal._p[1], normal._p[2]);
    float factor = 1.0f / n.length();
    _normal =
        new Point4(
            normal._p[0] * factor,
            normal._p[1] * factor,
            normal._p[2] * factor,
            normal._p[3] * factor);
  }

  public String toString() {
    return "Plane{ " + _normal + "}";
  }
}
