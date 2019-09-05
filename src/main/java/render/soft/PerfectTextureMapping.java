package render.soft;

import render.*;
import twod.Point2;
import twod.Screen;
import util.Point3;

public class PerfectTextureMapping implements TextureMapping {
  // we could also reduce the number of variables.
  private Point3 _a;
  private Point3 _b;
  private Point3 _c;
  private Texture _t;
  private int _textureWidth;
  private int _textureHeight;

  private float _sa; // S.dot(A)
  private float _saIncr;

  private float _sb;
  private float _sbIncr;
  private float _sc;
  private float _scIncr;

  private float _scInv;
  private SoftEngine _engine = (SoftEngine) Engine.getEngine();

  private float _uFloat;
  private float _vFloat;
  private Point3 _view = new Point3();

  private float _toViewXFac;
  private float _toViewYFac;

  public void calculateTextureMapping(Point3 p0, Point3 p1, Point3 p2, Texture t) {
    Point3 m = Point3.substraction(p2, p0);
    Point3 n = Point3.substraction(p1, p0);
    _a = p0.block(n);
    _b = m.block(p0);
    _c = n.block(m);
    _t = t;
    _textureWidth = _t.getX();
    _textureHeight = _t.getY();
    _a.scale(_textureWidth);
    _b.scale(_textureHeight);
    Screen screen = _engine.getScreen();
    // these are basically constants and shouldn't
    // be calculated again and again.
    _toViewXFac = 1.0f / ((screen.getX() - 1) * .5f);
    _toViewYFac = 1.0f / ((screen.getY() - 1) * .5f);
  }
  /** inner textureMapping loop: called up to something like 250 000 times a frame. */
  public final void storeUV(Point2 result) {
    _storeUVOptimized(result);
    // _storeUVSlow(result);
  }
  /** I don't think I can do better. */
  private final void _storeUVOptimized(Point2 result) {
    _scInv = 1.0f / _sc;

    _uFloat = _sa * _scInv;
    _vFloat = _sb * _scInv;

    result._x = (int) _uFloat;
    result._y = (int) _vFloat;
  }

  private void _storeUVSlow(Point2 result) {
    /*
     doesn't use the _s<whatever> increments.
     safe fall-back.
    */
    _sa = _view.dot(_a);
    _sb = _view.dot(_b);
    _sc = _view.dot(_c);
    _scInv = 1.0f / _sc;

    _uFloat = _sa * _scInv;
    _vFloat = _sb * _scInv;

    result._x = (int) _uFloat;
    result._y = (int) _vFloat;
  }

  public void initScanLine(Point2 screenCoords) {
    // we could probably factor out some products
    _view._p[0] = (screenCoords._x * _toViewXFac - 1);
    _view._p[1] = (screenCoords._y * _toViewYFac - 1);
    _view._p[2] = 1;

    // these could be optimised since _view._p[2]==1
    _sa = _view.dot(_a);
    _sb = _view.dot(_b);
    _sc = _view.dot(_c);

    _saIncr = _a._p[0] * _toViewXFac;
    _sbIncr = _b._p[0] * _toViewXFac;
    _scIncr = _c._p[0] * _toViewXFac;
  }

  public void nextScanLine() {}

  public void nextX() {
    _sa += _saIncr;
    _sb += _sbIncr;
    _sc += _scIncr;
  }
}
