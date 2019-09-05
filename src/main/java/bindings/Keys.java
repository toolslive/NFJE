package bindings;

import java.awt.*;
import java.awt.event.*;
import render.*;
import util.*;

public class Keys implements KeyListener {
  private boolean _soft = false;

  public Keys() {}

  public Keys(boolean soft) {
    _soft = soft;
  }

  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    if (code == 27) {
      System.exit(0);
    }
    VisualWorld world = VisualWorld.getVisualWorld();
    Camera camera = world.getCamera();
    Point3 dir = camera.direction();
    Point3 scaled = dir.scaled(0.1f);
    Point3 left = camera.left();
    left = left.scaled(0.1f);
    if (code == 83) { // 's'
      camera._position = camera._position.add(scaled);
      return;
    }
    if (code == 88) { // 'x'
      scaled = scaled.minus();
      camera._position = camera._position.add(scaled);
      return;
    }
    if (code == 65) { // 'a'
      if (!_soft) {
        left = left.minus();
      }
      camera._position = camera._position.add(left);
      return;
    }
    if (code == 68) { // 'd'
      if (_soft) {
        left = left.minus();
      }
      camera._position = camera._position.add(left);
      return;
    }
  }

  public void keyReleased(KeyEvent e) {}

  public void keyTyped(KeyEvent e) {}
}
