package render;

import java.util.Enumeration;
import util.*;

public class Cube implements Renderable, RenderableProvider {
  public String type() {
    return "Cube";
  }

  private Matrix44 _transformation;
  private float _size;
  private static final float CENTER_CORNER_DISTANCE = (float) Math.sqrt(3) * .5f;
  public Polygon[] polys;
  protected Point3L _points = new Point3L();
  protected Point2L _textureCoordinates = new Point2L();

  public Renderable getRenderable() {
    return this;
  }

  public Matrix44 getTransformation() {
    return _transformation;
  }

  public Cube(float size, Texture t) {
    _size = size;
    _transformation = Matrix44.createUnit();
    polys = new Polygon[6];

    // the cube is flattened like this:
    /*
           4xxx5
           x   x
           x   x
           x   x
           7xxx6xxx5xxx4xxx7
    x   x   x   x   x
           x   x   x   x   x
           x   x   x   x   x
    3xxx2xxx1xxx0xxx3
    x   x
    x   x
    x   x
    0xxx1
    */
    float x = 1.0f;
    _points.add(new Point3(-size, -size, -size));
    _textureCoordinates.add(new Point2(0, 0));

    _points.add(new Point3(size, -size, -size));
    _textureCoordinates.add(new Point2(x, 0));

    _points.add(new Point3(size, size, -size));
    _textureCoordinates.add(new Point2(x, x));

    _points.add(new Point3(-size, size, -size));
    _textureCoordinates.add(new Point2(0, x));

    _points.add(new Point3(-size, -size, size));
    _textureCoordinates.add(new Point2(0, x));

    _points.add(new Point3(size, -size, size));
    _textureCoordinates.add(new Point2(x, 3 * x));

    _points.add(new Point3(size, size, size));
    _textureCoordinates.add(new Point2(x, 2 * x));

    _points.add(new Point3(-size, size, size));
    _textureCoordinates.add(new Point2(0, 2 * x));

    polys[0] = new PolygonImpl(new int[] {0, 1, 2, 3}, _points, _textureCoordinates); // front
    polys[1] = new PolygonImpl(new int[] {0, 3, 7, 4}, _points, _textureCoordinates); // left

    polys[2] = new PolygonImpl(new int[] {5, 4, 7, 6}, _points, _textureCoordinates); // back

    polys[3] = new PolygonImpl(new int[] {1, 5, 6, 2}, _points, _textureCoordinates); // right

    polys[4] = new PolygonImpl(new int[] {3, 2, 6, 7}, _points, _textureCoordinates); // top

    polys[5] = new PolygonImpl(new int[] {1, 0, 4, 5}, _points, _textureCoordinates); // bottom
    for (int i = 0; i < 6; i++) {
      PolygonImpl polygon = (PolygonImpl) polys[i];
      polygon.setTexture(t);
    }
    _boundingSphere.setRadius(_size * CENTER_CORNER_DISTANCE);
    VisualWorld.register(this);
  }

  private Point3 _null = new Point3();
  private Sphere _boundingSphere = new Sphere();

  public BoundingVolume getBoundingVolume() {
    Point3 transformed = _transformation.applyOn(_null);
    _boundingSphere.setCenter(transformed);
    return _boundingSphere;
  }

  public Enumeration getChildren() {
    return NullEnumeration.NULL;
  }
}
