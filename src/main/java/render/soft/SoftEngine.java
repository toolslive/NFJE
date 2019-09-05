package render.soft;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import render.*;
import stats.*;
import twod.Point2;
import twod.Screen;
import util.*;

public class SoftEngine extends Engine {
  // constructors...
  private long _time = 0;
  private double _avg;
  private PolygonPipeline _pipeline;
  long _count;
  private TransformationStack _transformationStack = new TransformationStack();
  private Screen _screen;

  float _xFac; // x scaling
  float _xFac_1; // x scaling inverse.
  float _yFac;
  float _yFac_1; // y scaling inverse.

  public SoftEngine(Screen screen) {
    _screen = screen;
    _xFac = (_screen.getX() - 1) * .5f;
    _yFac = (_screen.getY() - 1) * .5f;
    _xFac_1 = 1.0f / _xFac;
    _yFac_1 = 1.0f / _yFac;
    _pipeline = new PolygonPipeline();
    new PolygonMeshRenderer();
    new CubeRenderer(this);
    new IcosahedronRenderer(this);
    new PerfectTextureMappingFactory();
  }

  public void pushTransformation(Matrix44 m) {
    _transformationStack.push(m);
  }

  public Matrix44 popTransformation() {
    return _transformationStack.pop();
  }

  public Matrix44 getTransformation() {
    return _transformationStack.get();
  }

  public Screen getScreen() {
    return _screen;
  }

  public void renderWorld() {
    newFrame();
    _pipeline.newFrame();
    Point3.constructed = 0;
    long begin = System.currentTimeMillis();
    VisualWorld world = VisualWorld.getVisualWorld();
    Camera camera = world.getCamera();
    camera.invalidate();
    Enumeration providers = world.renderableProviders();
    String type;
    _transformationStack.push(camera.getNormalizationMatrix());

    renderProviders(providers);

    _pipeline.flush();
    _transformationStack.pop();
    long end = System.currentTimeMillis();
    long time = end - begin;
    double fps = 1000.0 / time;
    Statistics.update("fps", Double.valueOf(fps));
    _avg = ((_avg * _count) + time) / (_count + 1);
    _count++;
    if (time > _time) {
      _time = time;
    }
    // System.out.println("avg="+_avg+" max="+_time);
  }

  public Point3 toCanonical(Point3 p) {
    Matrix44 transformation = getTransformation();
    Point3 p_ = transformation.applyOn(p);
    return p_;
  }

  public Point4 toCanonical(Point4 p) {
    Matrix44 transformation = getTransformation();
    Point4 p_ = transformation.applyOn(p);
    return p_;
  }

  public twod.Point2 to2D(Point3 canonical) {
    float f = 1.0f / canonical._p[2];
    float x = canonical._p[0] * f;
    float y = canonical._p[1] * f;
    int ix = (int) ((x + 1) * _xFac);
    int iy = (int) ((y + 1) * _yFac);
    Point2 p2 = new Point2(ix, iy);
    return p2;
  }

  public void toView(twod.Point2 screen, float z, Point3 result) {
    float x = ((screen._x * _xFac_1) - 1) * z;
    float y = ((screen._y * _yFac_1) - 1) * z;
    result._p[0] = x;
    result._p[1] = y;
    result._p[2] = z;
  }
}
