package com.example.vertx.models;

import com.example.vertx.util.EnumUtil;

public enum TransactionType {
  DEBIT, CREDIT;

  public static TransactionType fromString(String name) {
    return EnumUtil.getEnumFromString(TransactionType.class, name);
  }
}
