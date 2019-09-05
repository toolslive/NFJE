package util;

public class Camera {
  public Point3 _position;
  public float _alpha;
  public float _beta;
  public float _gamma;
  private boolean _validationN = false;
  private Matrix44 _n;
  private Matrix44 _nInverse;

  public Camera() {
    _position = new Point3();
  }

  public void invalidate() {
    _validationN = false;
  }

  public Point3 direction() {
    Point3 p = new Point3(0.0f, 0.0f, -1.0f);
    Matrix44 result = Matrix44.createUnit();
    Matrix44 rx = Matrix44.createXRotation(_alpha);
    Matrix44 ry = Matrix44.createYRotation(_beta);
    Matrix44 rz = Matrix44.createZRotation(_gamma);
    result = Matrix44.mult(rz, result);
    result = Matrix44.mult(rx, result);
    result = Matrix44.mult(ry, result);
    Point3 pV = result.applyOn(p);
    return pV;
  }

  public Point3 left() {
    Point3 p = new Point3(1.0f, 0.0f, 0.0f);
    Matrix44 result = Matrix44.createUnit();
    Matrix44 rx = Matrix44.createXRotation(_alpha);
    Matrix44 ry = Matrix44.createYRotation(_beta);
    Matrix44 rz = Matrix44.createZRotation(_gamma);
    result = Matrix44.mult(rz, result);
    result = Matrix44.mult(rx, result);
    result = Matrix44.mult(ry, result);
    Point3 pV = result.applyOn(p);
    return pV;
  }

  public Matrix44 getNormalizationMatrix() {
    Matrix44 result;
    if (_validationN) {
      result = _n;
    } else {
      result = Matrix44.createUnit();
      Matrix44 t = Matrix44.createTranslation(_position.minus());
      Matrix44 rx = Matrix44.createXRotation(-_alpha);
      Matrix44 ry = Matrix44.createYRotation(-_beta);
      Matrix44 rz = Matrix44.createZRotation(-_gamma);
      result = Matrix44.mult(t, result);
      result = Matrix44.mult(ry, result);
      result = Matrix44.mult(rx, result);
      result = Matrix44.mult(rz, result);
      _n = result;
      _validationN = true;
    }
    return result;
  }

  public Matrix44 getNormalizationMatrixInverse() {
    Matrix44 result;
    result = getNormalizationMatrix();
    result = result.affineInverse();
    return result;
  }

  public String toString() {
    return "{" + _position + "," + _alpha + "," + _beta + "," + _gamma + "}";
  }
}
