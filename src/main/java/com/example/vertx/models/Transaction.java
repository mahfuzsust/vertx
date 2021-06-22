package com.example.vertx.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Transaction {
  @JsonIgnore
  private String id;
  private String walletId;
  private Double amount;
  private TransactionType type;

  public Transaction() {
  }

  public Transaction(String id, String walletId, Double amount, TransactionType type) {
    this.id = id;
    this.walletId = walletId;
    this.amount = amount;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }
}
