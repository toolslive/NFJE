import bindings.*;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import render.*;
import render.soft.*;
import twod.*;

public class MainApplet extends Applet {
  RenderCanvas _renderCanvas;

  public void init() {
    io.RootDir.register(MainApplet.class);
    String worldClassName = getParameter("WorldClass");
    if (worldClassName == null) {
      worldClassName = "demo.MushroomAndCubes";
    }
    try {
      // instantiate the world to render.
      Class.forName(worldClassName).getDeclaredConstructor().newInstance();

      Dimension size = getSize();
      int width = size.width;
      int height = size.height;

      _renderCanvas = new SoftRenderCanvas(width, height);
      add((Canvas) _renderCanvas);
      _renderCanvas.addMouseMotionListener(new Mouse(true));
      _renderCanvas.addKeyListener(new Keys(true));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void paint(Graphics g) {
    Dimension d = getSize();
    setForeground(Color.black);
    getGraphics().fillRect(0, 0, d.width, d.height);
    setForeground(Color.black);
  }

  public void start() {
    System.out.println("start");
    _renderCanvas.start();
  }

  public void stop() {
    _renderCanvas.stop();
    VisualWorld.clean();
  }

  public void destroy() {
    stop();
    remove((Canvas) _renderCanvas);
    _renderCanvas = null;
  }
}
