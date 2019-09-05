package render;

import util.*;

public class PolygonImpl implements Polygon {
  public static long constructed = 0;
  int[] _indices;
  Point3L _points;
  Point2L _textureCoordinates;
  TextureMapping _textureMapping;
  Point4 _normal;
  int _nIndex; // index of the normal in the point list.

  private Texture _texture;

  public Matrix44 getTransformation() {
    return Matrix44.createUnit();
  }

  public PolygonImpl(int[] indices, Point3L points, Point2L textureCoordinates) {
    _indices = indices;
    _points = points;
    _calculateNormal();
    _textureCoordinates = textureCoordinates;
    constructed++;
  }

  public Point2 getTextureCoordinates(int pointNr) {
    int index = _indices[pointNr];
    return _textureCoordinates.getPoint(pointNr);
  }

  public Point2L getTextureCoordinates() {
    return _textureCoordinates;
  }

  public Texture getTexture() {
    return _texture;
  }

  public void setTexture(Texture t) {
    _texture = t;
  }

  public int numberOfPoints() {
    return _indices.length;
  }

  public Point3 getPoint(int pointNr) {
    int index = _indices[pointNr];
    return _points.getPoint(index);
  }
  /**
   * returns the point when going counterClockWise
   *
   * @param pointNR must be <0
   */
  public Point3 getPointMinus(int pointNr) {
    int cNr = _indices.length + pointNr;
    int index = _indices[cNr];
    return _points.getPoint(index);
  }

  public Point3 getG() {
    Point3 g = new Point3();
    Point3 p;
    for (int i = 0; i < numberOfPoints(); i++) {
      p = getPoint(i);
      g._p[0] += p._p[0];
      g._p[1] += p._p[1];
      g._p[2] += p._p[2];
    }
    g.scale(1.0f / numberOfPoints());
    return g;
  }

  public String toString() {
    String result = "Polygon[";
    for (int i = 0; i < numberOfPoints(); i++) {
      result += "" + getPoint(i) + " ";
    }
    result += " n=" + getNormal();
    result += " g=" + getG();
    result += "]";
    return result;
  }

  private void _calculateNormal() {
    Point3 p0 = getPoint(0);
    Point3 p1 = getPoint(1);
    Point3 p2 = getPoint(2);

    Point3 p10 = Point3.substraction(p1, p0);
    Point3 p20 = Point3.substraction(p2, p0);

    Point3 n = p10.block(p20);
    n.normalize();
    _normal = new Point4(n._p[0], n._p[1], n._p[2], 0.0f);
  }
  /** doesn't work yet (when it does, it should be more stable.) */
  private void _calculateNormal2() {
    float a = 0.0f, b = 0.0f, c = 0.0f;
    int size = _indices.length;
    Point3 pi;
    Point3 pj;
    for (int i = 0; i < size; i++) {
      int j = (i + 1) % size;
      pi = getPoint(i);
      pj = getPoint(j);
      a += (pi._p[2] + pj._p[2]) + (pj._p[1] - pi._p[1]);
      b += (pi._p[0] + pj._p[0]) + (pj._p[2] - pi._p[2]);
      c += (pi._p[1] + pj._p[1]) + (pj._p[0] - pi._p[0]);
    }
    a *= 0.5f;
    b *= 0.5f;
    c *= 0.5f;
    _normal = new Point4(a, b, c, 0.0f);
  }

  public Point4 getNormal() {
    return _normal;
  }

  public Point4 getNormal(int pointNr) {
    return _normal;
  }

  public String type() {
    return "Polygon";
  }

  public void calculateTextureMapping() {
    Point3 p0, p1, p2;
    p0 = getPoint(0);
    p1 = getPoint(1);
    p2 = getPointMinus(-1);
    TextureMappingFactory factory = TextureMappingFactory.getInstance();
    _textureMapping = factory.createTextureMapping();
    _textureMapping.calculateTextureMapping(p0, p1, p2, _texture);
  }

  public TextureMapping getTextureMapping() {
    return _textureMapping;
  }

  public void setTextureMapping(TextureMapping t) {
    _textureMapping = t;
  }

  public BoundingVolume getBoundingVolume() {
    return BoundingVolume.NULL;
  }
}
