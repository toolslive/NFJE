package render.soft;

import render.*;
import twod.Point2;
import twod.Screen;
import util.*;

/** PolygonRasterer is responsible for scanline conversion of polygons and applying texture. */
public class PolygonRasterer extends PolygonStage implements XYZProcessor {

  Screen _screen;
  int _color;
  Texture _texture;

  float _lightFac;

  float _screenxf;
  float _screenyf;
  int _screenx;
  int _screeny;
  float _screenx2;
  float _screeny2;
  int[] _pixels;
  SoftEngine _engine;
  Polygon _polygon;
  int[] _left;
  int[] _right;

  float[] _leftZ;
  float[] _rightZ;
  int _maxX;
  int _minX;

  ZBuffer _zBuffer;
  LineAlgo _lineAlgo;
  public static long COUNT = 0;

  public PolygonRasterer() {
    super(null);
    _lineAlgo = new LineAlgo();
    _lineAlgo.setXYZProcessor(this);
    _engine = (SoftEngine) Engine.getEngine();
    _screen = _engine.getScreen();
    _screenx = _screen.getX();
    _screeny = _screen.getY();
    _zBuffer = new ZBuffer(_screenx, _screeny);
    _screenxf = 1.0f / _screenx;
    _screenyf = 1.0f / _screeny;
    _screenx2 = 2 * _screenxf;
    _screeny2 = 2 * _screenyf;

    _left = new int[_screeny];
    _right = new int[_screeny];
    _leftZ = new float[_screeny];
    _rightZ = new float[_screeny];
    _maxX = _screen.getX();
    _minX = -2;
  }

  private void _reset() {
    for (int i = 0; i < _left.length; i++) {
      _left[i] = _maxX;
      _right[i] = _minX;
    }
  }

  protected String _toString() {
    return "Rasterer";
  }
  /**
   * Polygons enter the stage here. we override the accept method, since this is always the last
   * stage.
   */
  public void accept(Polygon p) {
    _reset();
    _polygon = p;
    _texture = _polygon.getTexture();
    _calculateShading();
    rasterPolygon();
  }

  public void newFrame() {
    _count = 0;
    _zBuffer.clear();
  }

  public void flush() {
    // we don't do anything here, since there is no next stage.
  }

  public String type() {
    return "Polygon";
  }

  public void rasterPolygon() {
    int size = _polygon.numberOfPoints();
    int j;
    Point3 p0, p1;
    for (int i = 0; i < size; i++) {
      j = (i + 1) % size;
      p0 = _polygon.getPoint(i);
      p1 = _polygon.getPoint(j);
      _processEdge(p0, p1);
    }
    _renderSpans();
  }

  private void _renderSpans() {
    float bufferZ, z, dz, zIncr;
    int x, y; // screen x,y
    float x_, y_; // viewspace x,y
    int u, v; // textureCoordinates
    int colorIndex;
    int color;
    TextureMapping textureMapping = _polygon.getTextureMapping();

    Point2 xy = new Point2(0, 0);
    Point2 uv = new Point2(0, 0);
    Point3 view = new Point3();
    for (y = 0; y < _screeny; y++) {

      z = _leftZ[y];
      if (_left[y] == _maxX && _right[y] == _minX) {
      } else {
        dz = _rightZ[y] - _leftZ[y];
        zIncr = 0.0f;
        int length = _right[y] - _left[y];
        if (length > 0) {
          zIncr = dz / length;
        }
        xy._x = _left[y];
        xy._y = y;
        textureMapping.initScanLine(xy /*,
					    z,
					    zIncr,
					    _right[y],
					    _rightZ[y]*/);
        for (x = _left[y]; x < _right[y]; x++) {
          bufferZ = _zBuffer.getZ(x, y);
          if (z > bufferZ) {
            _zBuffer.setZ(x, y, z);
            textureMapping.storeUV(uv);
            colorIndex = _texture.getIndex(uv);
            color = _texture.getPixel(colorIndex, _lightFac);
            _screen.drawPixel(x, y, color);
          }
          z += zIncr;
          textureMapping.nextX();
        }
        textureMapping.nextScanLine();
      }
    }
  }

  private void _calculateShading() {
    Camera camera = VisualWorld.getVisualWorld().getCamera();
    Point3 position = camera._position;
    Point3 g = _polygon.getG();
    Point3 cToP = Point3.substraction(g, position);
    cToP.normalize();
    Point4 normal = _polygon.getNormal();
    Point3 normal3 = normal.toPoint3();

    if (normal3._p[2] < 0) {
      // always have the same direction...
      normal3.scale(-1.0f);
    }
    normal3.normalize();
    // System.out.println(cToP+" "+normal3);
    float dot = normal3.dot(cToP);
    if (dot < 0.0f) {
      dot = 0.0f;
    }
    _lightFac = .3f + dot * .7f; // seems to be reasonable.
    // System.out.println("dot="+dot+" lightFac="+_lightFac);
  }

  public String dump() {
    String result = "";
    int size = _screeny;
    for (int y = 0; y < size; y++) {
      if (_left[y] == _maxX && _right[y] == _minX) {
      } else {
        result += "y:" + y;
        result += " x: " + _left[y];
        result += " <-> " + _right[y];
        result += " z: " + _leftZ[y];
        result += " <-> " + _rightZ[y];
        result += "\n";
      }
    }
    return result;
  }

  private void _processEdge(Point3 p0, Point3 p1) {
    Point2 p02 = _engine.to2D(p0);
    Point2 p12 = _engine.to2D(p1);
    _lineAlgo.drawLine(p02._x, p02._y, p0._p[2], p12._x, p12._y, p1._p[2]);
  }

  public void process(int x, int y, float z) {
    // assert(y>=0);
    // assert(x>=0);
    // assert(x<_maxX);
    // assert(y<_maxY);
    if (x < _left[y]) {
      _leftZ[y] = z;
      _left[y] = x;
    }
    if (x > _right[y]) {
      _rightZ[y] = z;
      _right[y] = x;
    }
  }
}
