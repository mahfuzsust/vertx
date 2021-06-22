package com.example.vertx.models;

public class Wallet {
  private String id;
  private Double balance;

  public Wallet() {}

  public Wallet(String id, Double balance) {
    this.id = id;
    this.balance = balance;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }
}
