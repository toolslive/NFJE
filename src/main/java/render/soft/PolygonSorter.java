package render.soft;

import java.util.Enumeration;
import java.util.Vector;
import render.*;
import util.Point3;

/** provides a front to back sort of the polygons. */
public class PolygonSorter extends PolygonStage {
  Vector<Polygon> _toSort = new Vector<Polygon>();
  final float d = 1e-4f;

  public PolygonSorter(PolygonStage next) {
    super(next);
  }

  protected Polygon _process(Polygon p) {
    _toSort.addElement(p);
    return null;
  }

  protected String _toString() {
    return "Z-sorter";
  }

  public void newFrame() {
    _toSort.removeAllElements();
    _next.newFrame();
  }

  private void _sort() {
    // System.out.println("sorting: "+_toSort.size());
    quickSort(0, _toSort.size() - 1);
  }

  protected int _compareTo(Polygon p1, Polygon p2) {
    Point3 p1g = p1.getG();
    Point3 p2g = p2.getG();
    float diff = p1g._p[2] - p2g._p[2];
    if (diff > d) {
      return -1;
    }
    if (diff < -d) {
      return 1;
    }
    return 0;
  }

  protected void quickSort(int i, int j) {
    if (i < j) {
      int k = _split(i, j);
      quickSort(i, k);
      quickSort(k + 1, j);
    }
  }

  protected int _split(int i, int j) {
    Polygon obj = _toSort.elementAt((i + j) / 2);
    int k = i;
    int l;
    int i1;
    for (l = j; k <= l; ) {
      while (_compareTo(_toSort.elementAt(k), obj) < 0) k++;
      for (; _compareTo(_toSort.elementAt(l), obj) > 0; l--) ;

      if (k <= l) {
        swap(k, l);
        k++;
        l--;
      }
    }
    if (l < i) i1 = i;
    else i1 = l;
    return i1;
  }

  protected void swap(int i, int j) {
    Polygon obj = _toSort.elementAt(i);
    _toSort.setElementAt(_toSort.elementAt(j), i);
    _toSort.setElementAt(obj, j);
  }

  public void flush() {
    _sort();
    Enumeration<Polygon> e = _toSort.elements();

    Polygon p;
    while (e.hasMoreElements()) {
      p = e.nextElement();
      _next.accept(p);
    }
    super.flush();
  }
}
