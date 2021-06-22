package com.example.vertx.dao;

import com.example.vertx.util.EnumUtil;

public enum FundType {
  TRANSFER, CARD, LOAD;

  public static FundType fromString(String name) {
    return EnumUtil.getEnumFromString(FundType.class, name);
  }
}
