package com.example.vertx.dao;

public class WalletBalance {
  private String wallet;
  private String callbackUrl;

  public WalletBalance() {
  }

  public WalletBalance(String wallet, String callbackUrl) {
    this.wallet = wallet;
    this.callbackUrl = callbackUrl;
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
}
