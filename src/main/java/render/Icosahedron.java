package render;

import java.util.Enumeration;
import util.*;

public class Icosahedron implements Renderable, RenderableProvider {
  public String type() {
    return "Icosahedron";
  }

  private Matrix44 _transformation;
  public PolygonImpl[] polys;
  protected Point3L _points = new Point3L();
  protected Point2L _textureCoordinates = new Point2L();

  public Renderable getRenderable() {
    return this;
  }

  public Matrix44 getTransformation() {
    return _transformation;
  }

  private static final float X = .525731112119133606f;
  private static final float Z = .850650808352039932f;

  public Icosahedron(Texture t) {
    _transformation = Matrix44.createUnit();
    _points.add(new Point3(-X, 0.0f, Z));
    _points.add(new Point3(X, 0.0f, Z));
    _points.add(new Point3(-X, 0.0f, -Z));
    _points.add(new Point3(X, 0.0f, -Z));

    _points.add(new Point3(0.0f, Z, X));
    _points.add(new Point3(0.0f, Z, -X));
    _points.add(new Point3(0.0f, -Z, X));
    _points.add(new Point3(0.0f, -Z, -X));

    _points.add(new Point3(Z, X, 0.0f));
    _points.add(new Point3(-Z, X, 0.0f));
    _points.add(new Point3(Z, -X, 0.0f));
    _points.add(new Point3(-Z, -X, 0.0f));

    polys = new PolygonImpl[20];

    polys[0] = new PolygonImpl(new int[] {1, 4, 0}, _points, _textureCoordinates);
    polys[1] = new PolygonImpl(new int[] {4, 9, 0}, _points, _textureCoordinates);
    polys[2] = new PolygonImpl(new int[] {4, 5, 9}, _points, _textureCoordinates);
    polys[3] = new PolygonImpl(new int[] {8, 5, 4}, _points, _textureCoordinates);
    polys[4] = new PolygonImpl(new int[] {1, 8, 4}, _points, _textureCoordinates);

    polys[5] = new PolygonImpl(new int[] {1, 10, 8}, _points, _textureCoordinates);
    polys[6] = new PolygonImpl(new int[] {10, 3, 8}, _points, _textureCoordinates);
    polys[7] = new PolygonImpl(new int[] {8, 3, 5}, _points, _textureCoordinates);
    polys[8] = new PolygonImpl(new int[] {3, 2, 5}, _points, _textureCoordinates);
    polys[9] = new PolygonImpl(new int[] {3, 7, 2}, _points, _textureCoordinates);

    polys[10] = new PolygonImpl(new int[] {3, 10, 7}, _points, _textureCoordinates);
    polys[11] = new PolygonImpl(new int[] {10, 6, 7}, _points, _textureCoordinates);
    polys[12] = new PolygonImpl(new int[] {6, 11, 7}, _points, _textureCoordinates);
    polys[13] = new PolygonImpl(new int[] {6, 0, 11}, _points, _textureCoordinates);
    polys[14] = new PolygonImpl(new int[] {6, 1, 0}, _points, _textureCoordinates);

    polys[15] = new PolygonImpl(new int[] {10, 1, 6}, _points, _textureCoordinates);
    polys[16] = new PolygonImpl(new int[] {11, 0, 9}, _points, _textureCoordinates);
    polys[17] = new PolygonImpl(new int[] {2, 11, 9}, _points, _textureCoordinates);
    polys[18] = new PolygonImpl(new int[] {5, 2, 9}, _points, _textureCoordinates);
    polys[19] = new PolygonImpl(new int[] {11, 2, 7}, _points, _textureCoordinates);

    for (int i = 0; i < 20; i++) {
      polys[i].setTexture(t);
    }
    VisualWorld.register(this);
  }

  public BoundingVolume getBoundingVolume() {
    return BoundingVolume.NULL;
  }

  public Enumeration getChildren() {
    return NullEnumeration.NULL;
  }
}
