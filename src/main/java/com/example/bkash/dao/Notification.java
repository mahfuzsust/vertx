package com.example.bkash.dao;

public class Notification {
  private String wallet;
  private String message;

  public Notification() {
  }

  public Notification(String wallet, String message) {
    this.wallet = wallet;
    this.message = message;
  }

  public String getWallet() {
    return wallet;
  }

  public void setWallet(String wallet) {
    this.wallet = wallet;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Notification{" +
      "wallet='" + wallet + '\'' +
      ", message='" + message + '\'' +
      '}';
  }
}
