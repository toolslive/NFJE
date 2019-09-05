package render;

import twod.Point2;

public class NullTexture implements Texture {
  int _r;
  int _g;
  int _b;

  public NullTexture(int color) {
    _r = (color >> 16) & 255;
    _g = (color >> 8) & 255;
    _b = color & 255;
  }

  public int getX() {
    return 256;
  }

  public int getY() {
    return 256;
  }

  public int[] getPixels() {
    return null;
  }

  public int getR(int index) {
    return _r;
  }

  public int getG(int index) {
    return _g;
  }

  public int getB(int index) {
    return _b;
  }

  public int getIndex(int u, int v) {
    return 0;
  }

  public int getIndex(Point2 uv) {
    return 0;
  }

  public int getPixel(int index) {
    int color = 0;
    color |= (int) (_r) << 16;
    color |= (int) (_g) << 8;
    color |= (int) (_b);
    return color;
  }

  public int getPixel(int index, float lightFac) {
    int color = 255 << 24;
    color |= (int) (_r * lightFac) << 16;
    color |= (int) (_g * lightFac) << 8;
    color |= (int) (_b * lightFac);
    return color;
  }
}
