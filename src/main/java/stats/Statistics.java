package stats;

public class Statistics {
  private static Statistics _instance;

  public static Statistics getInstance() {
    if (_instance == null) {
      new Statistics();
    }
    return _instance;
  }

  protected Statistics() {
    _instance = this;
  }

  public static void update(String key, Object value) {
    getInstance()._update(key, value);
  }

  protected void _update(String key, Object value) {}
}
