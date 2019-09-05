package util;

/**
 * Frustum representation. For now, we have 5 planes, and their normals point inward.
 *
 * @author $Author: evilsloot $
 */
public class Frustum implements BoundingVolume {
  private Point3 _origin;
  private Plane[] _planes = new Plane[5];
  static final float ZMIN = .01f;

  static final int FRONT = 0;
  static final int LEFT = 1;
  static final int BACK = 6;

  private static final Point4[] NORMALS =
      new Point4[] {
        new Point4(0.0f, 0.0f, -1.0f, -ZMIN), // FRONT	
        new Point4(0.0f, -1.0f, -1.0f, 0.0f), // LEFT
        new Point4(0.0f, 1.0f, -1.0f, 0.0f), // RIGHT
        new Point4(-1.0f, 0.0f, -1.0f, 0.0f), // TOP
        new Point4(1.0f, 0.0f, -1.0f, 0.0f), // BOTTOM
      };

  public Point3 getCenterPoint() {
    return _origin;
  }

  public Point3 getIntersection(Point3 eye, Point3 direction) {
    return _getIntersection(eye, direction);
  }

  /** got it from Graphic Gems II. I adapted it to our normal direction. */
  private Point3 _getIntersection(Point3 eye, Point3 direction) {
    int size = NORMALS.length;
    float tresult;
    Point4 norm; // normal of face hit
    Plane plane; // plane equation
    float tnear, tfar, t, vn, vd;
    int front = 0, back = 0; // front/back face # hit

    tnear = -1.0e+20f; // -HUGE_VAL
    tfar = +1.0e+20f; // tmax
    for (int i = 0; i < size; i++) {

      plane = _planes[i];

      vd = plane.dot(direction);
      vn = plane.distance(eye);
      if (vd == 0.0f) {
        // ray is parallel to plane -
        //   check if ray origin is inside plane's
        //   half-space
        if (vn < 0.0f) {
          return null;
        }

      } else {
        // ray not parallel - get distance to plane
        t = -vn / vd;
        if (vd > 0.0f) {

          if (t > tfar) {
            return null;
          }
          if (t > tnear) {
            // hit near face, update normal
            front = i;
            tnear = t;
          }
        } else {
          // back face - T is a far point

          if (t < tnear) {
            return null;
          }
          if (t < tfar) {
            // hit far face, update normal

            back = i;
            tfar = t;
          }
        }
      }
    }
    // survived all tests
    // Note: if ray originates on polyhedron,
    // may want to change 0.0 to some
    // epsilon to avoid intersecting the originating face.
    //
    if (tnear >= 0.0f) {
      // outside, hitting front face
      norm = _planes[front]._normal;
      tresult = tnear;
      return Point3.addition(eye, direction.scaled(tresult));
    } else {
      if (tfar < 1.0e+20f) {
        // inside, hitting back face
        norm = _planes[back]._normal;
        tresult = tfar;
        return Point3.addition(eye, direction.scaled(tresult));
      } else {
        // inside, but back face beyond tmax//
        return null;
      }
    }
  }

  public Frustum(Camera c) {
    Matrix44 t = c.getNormalizationMatrixInverse();
    Point4 n;
    float length;
    for (int i = 0; i < NORMALS.length; i++) {
      n = NORMALS[i];
      _planes[i] = new Plane(n);
      _planes[i].applyTransformation(t);
    }
    _origin = c._position;
  }

  public Plane getPlane(int i) {
    return _planes[i];
  }

  public int intersection(BoundingVolume p) {

    if (p == null) {
      return DONT_KNOW;
    }
    if (p instanceof Sphere) {
      Sphere s = (Sphere) p;
      return intersectionCo(s);
    }
    return DONT_KNOW;
    /* Looks cool but is way to slow.
    Integer result=new Integer(DONT_KNOW);
           try {
               result=(Integer)Covariance.dispatch(BoundingVolume.class,                                                this, "intersectionCo",
                                                   new Object[]{ p });

           } catch(Exception e) {
               System.out.println(e);
           }
           return result.intValue();
    */
  }

  public int intersectionCo(NullBoundingVolume v) {
    return DONT_KNOW;
  }

  public int intersectionCo(Sphere s) {
    Point3 cs = s.getCenterPoint();
    float rs = s.radius();
    boolean overlap = false;
    float c;
    for (int i = 0; i < NORMALS.length; i++) {
      c = _planes[i].distance(cs);
      if (c < -rs) return OUTSIDE;
      if (c < rs) {
        overlap = true;
      }
    }
    if (overlap) {
      return OVERLAP;
    } else {
      return INSIDE;
    }
  }
}
