package input;

import java.io.*;
import java.net.*;
import java.util.*;
import render.*;
import util.*;

public class InvReader {
  private PolygonMesh _mesh;

  public InvReader(InputStream in) throws Exception {
    InputStreamReader inr = new InputStreamReader(in);
    BufferedReader reader = new BufferedReader(inr);
    String line = reader.readLine();
    int numberOfVertices = 0;
    int numberOfPolygons = 0;
    StringTokenizer tokenizer = new StringTokenizer(line);
    String nOVString = tokenizer.nextToken();
    String nOPString = tokenizer.nextToken();
    numberOfVertices = Integer.parseInt(nOVString);
    numberOfPolygons = Integer.parseInt(nOPString);
    Point3L points = new Point3L();
    Point2L textureCoordinates = new Point2L();
    float x, y, z, u, v;
    float[] tx = new float[] {0, 1, 0};
    float[] ty = new float[] {0, 0, 1};
    for (int i = 0; i < numberOfVertices; i++) {
      line = reader.readLine();
      tokenizer = new StringTokenizer(line);
      x = parseFloat(tokenizer.nextToken());
      y = parseFloat(tokenizer.nextToken());
      z = parseFloat(tokenizer.nextToken());
      if (tokenizer.hasMoreTokens()) { // textureCoords
        u = parseFloat(tokenizer.nextToken());
        v = parseFloat(tokenizer.nextToken());
      } else {
        u = tx[i % 3];
        v = ty[i % 3];
      }
      textureCoordinates.add(new Point2(u, v));
      points.add(new Point3(x, y, z));
    }
    _mesh = new PolygonMeshImpl(points, textureCoordinates);
    for (int i = 0; i < numberOfPolygons; i++) {
      line = reader.readLine();
      tokenizer = new StringTokenizer(line);
      int nPoints = Integer.parseInt(tokenizer.nextToken());
      int[] indices = new int[nPoints];
      for (int j = 0; j < nPoints; j++) {
        indices[j] = Integer.parseInt(tokenizer.nextToken());
      }
      TextureProvider provider = TextureProvider.getInstance();
      Texture t = provider.getTexture(0);
      _mesh.addPolygon(indices, t);
    }
  }

  public PolygonMesh getMesh() {
    return _mesh;
  }

  public float parseFloat(String f) {
    float result = 0.0f;
    try {
      result = Float.parseFloat(f);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
