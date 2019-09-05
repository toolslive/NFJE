package util;

import java.util.Enumeration;

public class NullEnumeration implements Enumeration {
  public static final Enumeration NULL = new NullEnumeration();

  private NullEnumeration() {}

  public boolean hasMoreElements() {
    return false;
  }

  public Object nextElement() {
    return null;
  }
}
