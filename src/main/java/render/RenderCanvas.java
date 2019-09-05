package render;

import java.awt.event.*;

public interface RenderCanvas {
  public void addMouseListener(MouseListener m);

  public void addKeyListener(KeyListener k);

  public void addMouseMotionListener(MouseMotionListener m);

  public void start();

  public void stop();

  public void destroy();

  public void display();

  public void reshape(int width, int height);
}
