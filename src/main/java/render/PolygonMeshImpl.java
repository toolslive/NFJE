package render;

import java.util.*;
import util.*;

public class PolygonMeshImpl implements PolygonMesh, RenderableProvider {
  private Point3L _points;
  private Point2L _textureCoordinates;
  private Point3 _g;
  private Vector<Polygon> _polygons;
  private Matrix44 _transformation;

  public String type() {
    return "PolygonMesh";
  }

  public Renderable getRenderable() {
    return this;
  }

  public Matrix44 getTransformation() {
    return _transformation;
  }

  public Point3 getG() {
    return _g;
  }

  private void _calculateG() {

    float x = 0.0f;
    float y = x;
    float z = x;
    Point3 current;
    int size = _points.size();
    for (int i = 0; i < size; i++) {
      current = _points.getPoint(i);
      x += current._p[0];
      y += current._p[1];
      z += current._p[2];
    }
    float size_1 = 1.0f / size;
    x *= size_1;
    y *= size_1;
    z *= size_1;
    _g = new Point3(x, y, z);
  }

  public PolygonMeshImpl(Point3L list, Point2L textureCoordinates) {
    _transformation = Matrix44.createUnit();
    _points = list;
    _textureCoordinates = textureCoordinates;
    _polygons = new Vector<Polygon>();
    VisualWorld.register(this);
    _calculateG();
  }

  public void addPolygon(int[] indices, Texture texture) {
    PolygonImpl p = new PolygonImpl(indices, _points, _textureCoordinates);
    _polygons.addElement(p);
    p.setTexture(texture);
  }

  public int size() {
    return _polygons.size();
  }

  public Polygon getPolygon(int index) {
    return (Polygon) _polygons.elementAt(index);
  }

  public BoundingVolume getBoundingVolume() {
    return BoundingVolume.NULL;
  }

  public Enumeration getChildren() {
    return NullEnumeration.NULL;
  }

  public String toString() {
    String result = "";
    for (int i = 0; i < _polygons.size(); i++) {
      result += _polygons.elementAt(i).toString() + "\n";
    }
    result += "\n" + _textureCoordinates;
    return result;
  }
}
