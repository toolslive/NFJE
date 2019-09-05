package render;

import twod.Point2;

public class TextureImpl implements Texture {
  int[] _a;
  int[] _r;
  int[] _g;
  int[] _b;
  int _x;
  int _y;

  public TextureImpl(int[] pixels, int x, int y) {
    _x = x;
    _y = y;
    int size = pixels.length;
    _a = new int[size];
    _r = new int[size];
    _g = new int[size];
    _b = new int[size];
    int color;
    for (int i = 0; i < size; i++) {
      color = pixels[i];
      _a[i] = (color & 0xff000000) >> 24;
      _r[i] = (color & 0x00ff0000) >> 16;
      _g[i] = (color & 0x0000ff00) >> 8;
      _b[i] = (color & 0x000000ff);
    }
  }

  public int getX() {
    return _x;
  }

  public int getY() {
    return _y;
  }

  public String toString() {
    return "Texture(" + _x + "," + _y + ")";
  }

  public int getR(int index) {
    return _r[index];
  }

  public int getG(int index) {
    return _g[index];
  }

  public int getB(int index) {
    return _b[index];
  }

  public int getIndex(int x, int y) {
    int index = y * _x + x;
    // these checks shouldn't be necessary:
    // just make the texture much bigger
    while (index >= _r.length) {
      index -= _r.length;
    }
    while (index < 0) {
      index += _r.length;
    }
    return index;
  }

  public int getIndex(Point2 uv) {
    return getIndex(uv._x, uv._y);
  }

  public int getPixel(int index) {
    int color;
    color = _a[index] << 24;
    color |= _r[index] << 16;
    color |= _g[index] << 8;
    color |= _b[index];
    return color;
  }

  public int getPixel(int index, float lightFac) {
    int color;
    color = _a[index] << 24;
    color |= (int) (_r[index] * lightFac) << 16;
    color |= (int) (_g[index] * lightFac) << 8;
    color |= (int) (_b[index] * lightFac);
    return color;
  }
}
