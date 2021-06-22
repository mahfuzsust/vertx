package com.example.bkash.models;

import com.example.bkash.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CardType {
  VISA("visa"), MASTERCARD("mastercard");

  private String value;
  CardType(String val) {
    this.value = val;
  }

  @JsonValue
  public String getValue() { return this.value; }

  public static CardType fromString(String name) {
    return EnumUtil.getEnumFromString(CardType.class, name);
  }
}
