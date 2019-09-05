package render;

import util.Point3;

public interface PolygonMesh extends Renderable {

  public Polygon getPolygon(int index);

  public void addPolygon(int[] indices, Texture t);

  public Point3 getG();
  /** number of polygons */
  public int size();
}
