package com.example.vertx.dao;

public class PaymentDao {
  private String wallet;
  private String callbackUrl;
  private Double amount;

  public PaymentDao() {
  }

  public PaymentDao(String wallet, String callbackUrl, Double amount) {
    this.wallet = wallet;
    this.callbackUrl = callbackUrl;
    this.amount = amount;
  }

  public String getWallet() {
    return wallet;
  }

  public void setWallet(String wallet) {
    this.wallet = wallet;
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }

  public void setCallbackUrl(String callbackUrl) {
    this.callbackUrl = callbackUrl;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}
