package com.example.vertx.util;

public class EnumUtil {
  /**
   * A common method for all enums since they can't have another base class
   * @param <T> Enum type
   * @param c enum type. All enums must be all caps.
   * @param string case insensitive
   * @return corresponding enum, or null
   */
  public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
    if( c != null && string != null ) {
      return Enum.valueOf(c, string.trim().toUpperCase());
    }
    return null;
  }
}
