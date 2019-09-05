package render;

import twod.Point2;

public interface Texture {
  public int getX();

  public int getY();

  public int getPixel(int index);

  public int getIndex(int u, int v);

  public int getIndex(Point2 uv);

  public int getR(int index);

  public int getG(int index);

  public int getB(int index);

  public int getPixel(int index, float lightFac);
}
