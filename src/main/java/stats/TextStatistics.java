package stats;

public class TextStatistics extends Statistics {
  protected void _update(String key, Object value) {
    System.out.println("" + key + " " + value);
  }
}
