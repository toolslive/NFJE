package render.soft;

import render.*;
import util.*;

public class PolygonClipper extends PolygonStage {
  public PolygonClipper(PolygonStage next) {
    super(next);
  }

  float _zmin = .01f;
  Plane _znear = new Plane(new Point4(0.0f, 0.0f, -1.0f, -_zmin));
  Plane _left = new Plane(new Point4(0.0f, -1.0f, -1.0f, 0.0f));
  Plane _right = new Plane(new Point4(0.0f, 1.0f, -1.0f, 0.0f));
  Plane _top = new Plane(new Point4(-1.0f, 0.0f, -1.0f, 0.0f));
  Plane _bottom = new Plane(new Point4(1.0f, 0.0f, -1.0f, 0.0f));
  /** reduces object creation... */
  private Point3 _temp = new Point3();

  protected String _toString() {
    return "Clipper";
  }

  protected Polygon _process(Polygon in) {
    in.calculateTextureMapping();
    Polygon result = general(in, _znear);

    if (result != null) {
      result = general(result, _left);
    }
    if (result != null) {
      result = general(result, _right);
    }
    if (result != null) {
      result = general(result, _top);
    }
    if (result != null) {
      result = general(result, _bottom);
    }
    return result;
  }

  /**
   * Given a plane defined by n and P Given vertices Q[0], Q[1], ..., Q[length-1] Let pd = 0
   *
   * <p>for each vertex i
   *
   * <p>Let d = n dot (Q[i] - P)
   *
   * <p>if d times pd < 0 then calculate I = Q[i-1] + t ( Q[i] - Q[i-1] ) with t = pd / ( pd - d )
   * insert I into the "in" list endif
   *
   * <p>if d >= 0 then insert Q[i] into the "in" list endif
   *
   * <p>Let pd = d end for loop
   *
   * <p>Let d = n dot (Q[0] - P)
   *
   * <p>If d times pd < 0 then calculate I = Q[length-1] + t ( Q[0] - Q[length-1] ) with t = pd / (
   * pd - d ) insert I into the "in" list endif
   */
  public Polygon general(Polygon q, Plane p) {
    int size = q.numberOfPoints();
    PolygonImpl result = null;
    float pd = 0.0f;
    float d, t;
    Point3 qi, intersection;
    Point3 qimin;
    Point3L in = new Point3L();
    Point2L textureCoordinates = new Point2L();
    for (int i = 0; i < size; i++) {

      qi = q.getPoint(i);
      d = p.distance(qi);
      if (d * pd < 0) {
        qimin = q.getPoint(i - 1);
        t = pd / (pd - d);
        Point3.substraction(qi, qimin, _temp);
        _temp.scale(t);
        intersection = Point3.addition(qimin, _temp);
        in.add(intersection);
      }
      if (d >= 0) {
        in.add(qi);
      }
      pd = d;
    }
    d = p.distance(q.getPoint(0));
    if (d * pd < 0) {
      Point3.substraction(q.getPoint(0), q.getPoint(size - 1), _temp);
      t = pd / (pd - d);
      _temp.scale(t);
      intersection = Point3.addition(q.getPoint(size - 1), _temp);
      in.add(intersection);
    }
    int newSize = in.size();
    if (newSize > 2) {
      int[] indices = new int[newSize];
      for (int i = 0; i < in.size(); i++) {
        indices[i] = i;
      }
      result = new PolygonImpl(indices, in, textureCoordinates);
      result.setTexture(q.getTexture());
      result.setTextureMapping(q.getTextureMapping());

    } else {
      result = null;
    }
    return result;
  }
}
