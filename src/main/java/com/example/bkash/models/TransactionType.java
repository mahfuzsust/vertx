package com.example.bkash.models;

import com.example.bkash.util.EnumUtil;

public enum TransactionType {
  DEBIT, CREDIT;

  public static TransactionType fromString(String name) {
    return EnumUtil.getEnumFromString(TransactionType.class, name);
  }
}
