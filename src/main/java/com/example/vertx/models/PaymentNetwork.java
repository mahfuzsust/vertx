package com.example.vertx.models;

import com.example.vertx.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentNetwork {
  STRIPE("stripe"), AMAZON("amazon"), GOOGLE("google");

  private String value;
  PaymentNetwork(String val) {
    this.value = val;
  }

  public static PaymentNetwork fromString(String name) {
    return EnumUtil.getEnumFromString(PaymentNetwork.class, name);
  }

  @JsonValue
  public String getValue() { return this.value; }
}
