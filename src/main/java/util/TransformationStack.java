package util;

import java.util.*;

public class TransformationStack {
  private Vector<Matrix44> _matrices = new Vector<Matrix44>();

  public TransformationStack() {}

  public void push(Matrix44 m) {
    int size = _matrices.size();
    if (_matrices.size() == 0) {
      _matrices.addElement(m);
    } else {
      Matrix44 top = _matrices.elementAt(size - 1);
      Matrix44 newTop = Matrix44.mult(top, m);
      _matrices.addElement(newTop);
    }
  }

  public Matrix44 pop() {
    int topIndex = _matrices.size() - 1;
    Matrix44 top = _matrices.elementAt(topIndex);
    _matrices.removeElementAt(topIndex);
    return top;
  }

  public Matrix44 get() {
    int topIndex = _matrices.size() - 1;
    return _matrices.elementAt(topIndex);
  }

  public int size() {
    return _matrices.size();
  }

  public Point3 applyOn(Point3 p) {
    int topIndex = _matrices.size() - 1;
    Matrix44 top = _matrices.elementAt(topIndex);
    return top.applyOn(p);
  }
}
