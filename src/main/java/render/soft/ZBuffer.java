package render.soft;

public class ZBuffer {
  private float[] _z;
  public static float NEG_INFINITY = -1.0e+10f;
  private int _width;

  public ZBuffer(int x, int y) {
    _z = new float[y * x];
    _width = x;
  }

  public void clear() {
    long begin = System.currentTimeMillis();
    for (int i = 0; i < _z.length; i++) {
      _z[i] = NEG_INFINITY;
    }
    long end = System.currentTimeMillis() - begin;
  }

  public float getZ(int x, int y) {
    return _z[y * _width + x];
  }

  public void setZ(int x, int y, float z) {
    _z[y * _width + x] = z;
  }
}
