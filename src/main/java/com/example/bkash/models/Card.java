package com.example.bkash.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Card {
  @JsonIgnore
  private String id;
  private String wallet;
  private String number;
  private CardType type;
  private PaymentNetwork network;

  public Card() {
  }

  public Card(String id, String wallet, String number, CardType type, PaymentNetwork network) {
    this.id = id;
    this.wallet = wallet;
    this.number = number;
    this.type = type;
    this.network = network;
  }

  public String getWallet() {
    return wallet;
  }

  public void setWallet(String wallet) {
    this.wallet = wallet;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public CardType getType() {
    return type;
  }

  public void setType(CardType type) {
    this.type = type;
  }

  public PaymentNetwork getNetwork() {
    return network;
  }

  public void setNetwork(PaymentNetwork network) {
    this.network = network;
  }
}
