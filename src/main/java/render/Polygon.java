package render;

import util.*;

public interface Polygon extends Renderable {
  public int numberOfPoints();
  /*
   * returns the point with index pointNr.
   */
  public Point3 getPoint(int pointNr);
  /**
   * returns the Point when going the other way around
   *
   * @param pointNr <0
   */
  public Point3 getPointMinus(int pointNr);

  public Point2 getTextureCoordinates(int pointNr);

  public Point2L getTextureCoordinates();
  /** returns the normal of the polygon's plane */
  public Point4 getNormal();
  /** returns the normal in that point. */
  public Point4 getNormal(int pointNr);

  public Point3 getG();

  public Texture getTexture();

  public void calculateTextureMapping();

  public void setTextureMapping(TextureMapping t);

  public TextureMapping getTextureMapping();
}
