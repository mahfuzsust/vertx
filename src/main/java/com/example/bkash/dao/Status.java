package com.example.bkash.dao;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
  SUCCESS("success"), FAILED("failed");

  private String value;
  Status(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() { return this.value; }
}
