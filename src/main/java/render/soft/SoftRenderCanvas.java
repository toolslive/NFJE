package render.soft;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import render.*;
import twod.*;

public class SoftRenderCanvas extends Canvas implements RenderCanvas, Runnable {
  Screen _screen;
  SoftEngine _engine;
  int _height;
  int _width;
  Graphics _offScreenGraphics;
  Image _offScreenImage;
  Image _img;
  Dimension _offScreenSize;

  Thread _thread;
  boolean _done;

  public SoftRenderCanvas(int x, int y) {
    setSize(x, y);
    preInit();
    init();
  }

  public void preInit() {}

  public void init() {
    Dimension size = getSize();
    _width = size.width;
    _height = size.height;
    _screen = new Screen(_width, _height);
    int[] pixels = _screen.getPixels();
    MemoryImageSource source = new MemoryImageSource(_width, _height, pixels, 0, _width);

    _img = createImage(source);
    _offScreenSize = new Dimension(_width, _height);

    _engine = new SoftEngine(_screen);
  }

  public void start() {
    _thread = new Thread(this, "swapper");
    _thread.start();
  }

  public void run() {
    while (_thread != null) {
      animate();
      xpaint();
      update(getGraphics());
      Thread.yield(); // needed ?
    }
  }

  public void stop() {
    _thread = null;
    System.out.println("stopped");
  }

  public void destroy() {
    _thread = null;
  }

  public void display() {}

  public void reshape(int width, int height) {}

  public void xpaint() {
    _screen.clear();
    _engine.renderWorld();
    _img.flush();
  }

  public void paint(Graphics g) {
    if (_img != null) {
      g.drawImage(_img, 0, 0, null);
    }
  }

  public void _paint(Graphics g) {
    g.drawImage(_img, 0, 0, null);
  }

  public void animate() {
    VisualWorld.update();
  }

  public void update(Graphics g) {
    if (_offScreenImage == null) {
      _offScreenImage = createImage(_width, _height);
    } else {
      _offScreenGraphics = _offScreenImage.getGraphics();
      _offScreenGraphics.fillRect(0, 0, _width, _height);
      _paint(_offScreenGraphics);
      g.drawImage(_offScreenImage, 0, 0, null);
    }
  }
}
