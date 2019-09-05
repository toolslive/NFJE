package render;
/** Java 1.1 can't have null implementations in interfaces. */
public interface Renderer {
  public void render(Renderable r);

  public String type();
}
