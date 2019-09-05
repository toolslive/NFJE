package io;

import java.io.InputStream;

public class RootDir {
  private static Class _startup;

  public static void register(Class clazz) {
    _startup = clazz;
  }

  public static InputStream getResourceAsStream(String resourceName) {
    return _startup.getResourceAsStream(resourceName);
  }
}
