package com.example.vertx.common;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log {
  public static Logger get() {
    return get("okapi");
  }

  public static Logger get(Class<?> cl) {
    return LogManager.getLogger(cl);
  }

  public static Logger get(String name) {
    return LogManager.getLogger(name);
  }
}
