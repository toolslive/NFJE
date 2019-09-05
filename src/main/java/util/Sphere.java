package util;

public class Sphere implements BoundingVolume {
  public static long constructed = 0L;
  protected Point3 _center;
  protected float _r;

  public Sphere() {
    this(new Point3(), 0.0f);
  }

  public void setCenter(Point3 center) {
    _center = center;
  }

  public void setRadius(float r) {
    _r = r;
  }

  public Sphere(Point3 center, float r) {
    constructed++;
    _center = center;
    _r = r;
  }

  public Point3 getIntersection(Point3 eye, Point3 direction) {
    Point3 l = Point3.substraction(_center, eye);
    float d = l.dot(direction);
    float l2 = l.dot(l);
    float r2 = _r * _r;
    if ((d < 0) && (l2 > r2)) {
      return null;
    }
    float m2 = l2 - d * d;
    if (m2 > r2) {
      return null;
    }
    float q = (float) Math.sqrt(r2 - m2);
    float t;
    if (l2 > r2) {
      t = d - q;
    } else {
      t = d + q;
    }
    Point3 result =
        new Point3(
            eye._p[0] + t * direction._p[0],
            eye._p[1] + t * direction._p[1],
            eye._p[2] + t * direction._p[2]);
    return result;
  }

  public Point3 getCenterPoint() {
    return _center;
  }

  public int intersection(BoundingVolume p) {
    Integer result = Integer.valueOf(DONT_KNOW);
    try {
      result =
          (Integer)
              Covariance.dispatch(BoundingVolume.class, this, "intersectionCo", new Object[] {p});

    } catch (Exception e) {
      System.out.println(e);
    }
    return result.intValue();
  }

  public float radius() {
    return _r;
  }

  public String toString() {
    return "Sphere{ " + _center + "," + _r + "}";
  }

  public int intersectionCo(BoundingVolume p) {
    return DONT_KNOW;
  }

  public int intersectionCo(Sphere p) {
    Point3 c1 = getCenterPoint();
    Point3 c2 = p.getCenterPoint();
    Point3 c2mc1 = Point3.substraction(c2, c1);
    float distance = c2mc1.length();
    int result = OVERLAP;
    float radius = radius();
    float r2 = p.radius();
    if (distance > (radius + r2)) {
      result = OUTSIDE;
    } else if ((distance + r2) < radius) {
      result = INSIDE;
    } else if ((distance + radius) < r2) {
      result = OUTSIDE;
    }
    return result;
  }
}
