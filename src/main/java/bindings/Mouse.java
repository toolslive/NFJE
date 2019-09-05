package bindings;

import java.awt.*;
import java.awt.event.*;
import render.*;
import util.*;

public class Mouse implements MouseMotionListener {
  private float _alphaFactor;
  private float _betaFactor;

  public Mouse() {
    this(false);
  }

  public Mouse(boolean soft) {
    if (soft) {
      _alphaFactor = 1.0f;
      _betaFactor = 1.0f;
    } else {
      _alphaFactor = 1.0f;
      _betaFactor = -1.0f;
    }
  }

  public void mouseDragged(MouseEvent e) {}

  public void mouseMoved(MouseEvent e) {
    Component comp = (Component) e.getSource();
    Dimension size = comp.getSize();
    float cx = size.width / 2.0f;
    float cy = size.height / 2.0f;
    int x = e.getX();
    int y = e.getY();
    float dx = x - cx;
    float dy = y - cy;
    float wx = dx / cx;
    float wy = dy / cy;

    float beta = (float) (2.0 * Math.atan(wx));
    float alpha = (float) (-2.0 * Math.atan(wy));
    VisualWorld world = VisualWorld.getVisualWorld();
    Camera camera = world.getCamera();
    camera._beta = beta * _betaFactor;
    camera._alpha = alpha * _alphaFactor;

    //	System.out.println(camera);

  }
}
