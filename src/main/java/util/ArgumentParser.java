package util;

import java.util.*;

/**
 * Allows you to process argument lists. The format is this: -level=5 firstParameter -verbose
 * secondParameter /log=false
 */
public class ArgumentParser {
  private Hashtable<String, String> _options = new Hashtable<String, String>();
  private Vector<String> _parameters = new Vector<String>();

  public ArgumentParser(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].startsWith("-") || args[i].startsWith("/")) {
        int location = args[i].indexOf("=");
        String key = location > 0 ? args[i].substring(1, location) : args[i].substring(1);
        String value = location > 0 ? args[i].substring(location + 1) : "";
        _options.put(key.toLowerCase(), value);
      } else {
        _parameters.addElement(args[i]);
      }
    }
  }

  public boolean hasOption(String option) {
    return _options.containsKey(option.toLowerCase());
  }

  public String getOption(String option) {
    return (String) _options.get(option.toLowerCase());
  }

  public Enumeration parameters() {
    return _parameters.elements();
  }
}
