package demo;

import input.*;
import java.io.*;
import java.net.*;
import java.util.Random;
import render.*;
import util.*;

public class MushroomAndCubes implements Updater {
  public Cube _cube;
  public Icosahedron _icos;
  public Random _random = new Random(0L);
  private int count = 1;
  private int dir = 0;

  public MushroomAndCubes() throws Exception {
    Texture t = null;
    TextureProvider provider = TextureProvider.getInstance();
    t = provider.getTexture(0);
    _cube = new Cube(0.2f, new NullTexture(-134567));
    _cube.getTransformation().translate(new Point3(0.0f, 0.0f, -1.0f));
    addInv();
    Updaters.getInstance().register(_cube, this);
    addCubes(300);
  }

  public void update(RenderableProvider provider) {
    if (count == 0) {
      dir = (int) (_random.nextDouble() * 3.0);
    }
    count += 1;
    count %= 40 * 500;
    float angle = .03f;
    if (provider != null) {
      switch (dir) {
        case 0:
          provider.getTransformation().rotateX(angle);
          break;
        case 1:
          provider.getTransformation().rotateY(angle);
          break;
        case 2:
          provider.getTransformation().rotateZ(angle);
          break;
      }
    }
  }

  public void addInv() throws Exception {
    InputStream in = io.RootDir.getResourceAsStream("data/world.properties");
    InvReader inv = new InvReader(in);
    in.close();
    PolygonMesh mesh = inv.getMesh();
    PolygonMeshImpl impl = (PolygonMeshImpl) mesh;
    Matrix44 transformation = impl.getTransformation();
    Point3 g = mesh.getG();
    transformation.translate(g.minus());
    transformation.translate(new Point3(0.0f, 0.0f, -1.0f));
    // System.out.println(mesh);
  }

  public void addCubes(int number) {
    float x;
    float y;
    float z;
    final float S = 25.0f;
    float angle;
    Updaters updaters = Updaters.getInstance();
    Texture t = null;
    TextureProvider provider = TextureProvider.getInstance();
    t = provider.getTexture(0);
    // t=new NullTexture(-6540212);
    for (int i = 0; i < number; i++) {
      Cube c = new Cube(0.2f, t);
      x = -S * .5f + _random.nextFloat() * S;
      y = -S * .5f + _random.nextFloat() * S;
      z = -S * .5f + _random.nextFloat() * S;
      Point3 tr = new Point3(x, y, z);
      Matrix44 m = c.getTransformation();
      c.getTransformation().translate(tr);
      angle = _random.nextFloat() * 1.5f;
      int dir = (int) (_random.nextFloat() * 2.9f);
      switch (dir) {
        case 0:
          m.rotateX(angle);
          break;
        case 1:
          m.rotateY(angle);
          break;
        case 2:
          m.rotateZ(angle);
          break;
      }
      Updaters.getInstance().register(c, this);
    }
  }
}
