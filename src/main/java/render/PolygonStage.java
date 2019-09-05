package render;

import stats.Statistics;

public class PolygonStage {
  protected PolygonStage _next;
  protected int _count = 0;

  public PolygonStage(PolygonStage next) {
    _next = next;
  }

  public String toString() {
    return _toString() + " -> " + _next;
  }

  protected String _toString() {
    return "Stage";
  }
  /** prepares to process polygons and let's the next stage to the same. */
  public void newFrame() {
    Statistics.update(_toString(), "" + _count);
    _count = 0;
    _next.newFrame();
  }

  /**
   * tell this stage to handle a polygon, after processing if might be sent to the next stage right
   * away, or at a later point
   */
  public void accept(Polygon polygon) {
    Polygon toNext = _process(polygon);
    if (toNext != null) {
      _count++;
      _next.accept(toNext);
    }
  }

  /**
   * makes sure that all polygons that have been accumulated in this stage and not yet sent to the
   * next stage will be sent to the next stage.
   */
  public void flush() {
    // System.out.println(_toString()+ ".count=="+_count);
    _next.flush();
  }
  /** handles the polygon, returns null if the next stage shouldn't do anything (at this point); */
  protected Polygon _process(Polygon polygon) {
    return polygon;
  }
}
