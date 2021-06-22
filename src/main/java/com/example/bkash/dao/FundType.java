package com.example.bkash.dao;

import com.example.bkash.models.CardType;
import com.example.bkash.util.EnumUtil;

public enum FundType {
  TRANSFER, CARD, LOAD;

  public static FundType fromString(String name) {
    return EnumUtil.getEnumFromString(FundType.class, name);
  }
}
