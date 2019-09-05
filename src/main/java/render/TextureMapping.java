package render;

import twod.Point2;
import util.Point3;

public interface TextureMapping {
  public void storeUV(Point2 result);

  public void calculateTextureMapping(Point3 p0, Point3 p1, Point3 p_1, Texture t);

  public void initScanLine(Point2 screenXY /*,float z,float zIncr,
					      int endX,float endZ*/);

  public void nextScanLine();

  public void nextX();
}
