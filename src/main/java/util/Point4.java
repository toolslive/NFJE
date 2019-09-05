package util;

public class Point4 {
  public static long constructed = 0;
  public float[] _p;

  public Point4() {
    _p = new float[4];
    constructed++;
  }

  public Point4(float x, float y, float z, float t) {
    this();
    _p[0] = x;
    _p[1] = y;
    _p[2] = z;
    _p[3] = t;
  }

  public Point3 toPoint3() {
    if (_p[3] == 0.0f) {
      return new Point3(_p[0], _p[1], _p[2]);
    } else {
      float f = 1.0f / _p[3];
      return new Point3(_p[0] * f, _p[1] * f, _p[2] * f);
    }
  }

  public Point4(Point3 p) {
    this();
    for (int i = 0; i < 3; i++) {
      _p[i] = p._p[i];
    }
    _p[3] = 1.0f;
  }

  public Point4(Point3 p, float t) {
    this(p);
    _p[3] = t;
  }
  /* appearantly this is not used.

     public void copy(Point4 p){
  for(int i=0;i<3;i++){
      _p[i]=p._p[i];
  }
     }


     public Point4 add(Point4 p2){
  Point4 result=new Point4(_p[0]+p2._p[0],
  			 _p[1]+p2._p[1],
  			 _p[2]+p2._p[2],
  			 _p[3]+p2._p[3]);
  return result;
     }
     */
  public String toString() {
    return "(" + _p[0] + "," + _p[1] + "," + _p[2] + "," + _p[3] + ")";
  }
}
