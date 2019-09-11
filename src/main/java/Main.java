import bindings.*;
import java.awt.*;
import java.lang.reflect.*;
import render.*;
import stats.TextStatistics;
import util.*;

public class Main {

  public static void main(String[] argv) throws Exception {
    io.RootDir.register(Main.class);
    Frame f = new Frame("Test");
    ArgumentParser parser = new ArgumentParser(argv);
    int x = 1024;
    int y = 1024;
    f.setSize(x, y);
    if (parser.hasOption("statistics")) {
      new TextStatistics();
    }

    RenderCanvas renderCanvas;
    Mouse mouse;
    Keys keys;
    String worldClassName;
    if (parser.hasOption("WorldClass")) {
      worldClassName = parser.getOption("WorldClass");

    } else {
      worldClassName = "demo.MushroomAndCubes";
    }
    // instantiate the world.
    System.out.println("instantiating " + worldClassName);
    Class.forName(worldClassName).getDeclaredConstructor().newInstance();

    if (!parser.hasOption("opengl")) {
      renderCanvas = _create("render.soft.SoftRenderCanvas", x, y);
      mouse = new Mouse(true);
      keys = new Keys(true);
    } else {
      // use reflection because the classes may not be available.
      // at class load time.
      renderCanvas = _create("render.opengl.OpenGLRenderCanvas", x, y);
      mouse = new Mouse(false);
      keys = new Keys(false);
    }
    renderCanvas.addMouseMotionListener(mouse);
    renderCanvas.addKeyListener(keys);

    VisualWorld vw = VisualWorld.getVisualWorld();
    f.add((Canvas) renderCanvas);
    f.setVisible(true);
    f.getGraphics().drawString("ugh!", 10, 10);
    System.out.println("go");
    renderCanvas.start();
  }

  private static RenderCanvas _create(String className, int x, int y) throws Exception {
    Class clazz = Class.forName(className);
    Constructor constructor = clazz.getConstructor(new Class[] {int.class, int.class});
    Object[] params = new Object[] {Integer.valueOf(x), Integer.valueOf(y)};

    RenderCanvas result = (RenderCanvas) constructor.newInstance(params);
    return result;
  }
}
