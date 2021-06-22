package com.example.vertx.dao;

import com.example.vertx.models.CardType;
import com.example.vertx.models.PaymentNetwork;

public class FundDao {
  private String wallet;
  private String cardNumber;
  private Boolean saveCard;
  private PaymentNetwork network;
  private CardType cardType;
  private Status status;
  private Double amount;

  public FundDao() {
  }

  public String getWallet() {
    return wallet;
  }

  public void setWallet(String wallet) {
    this.wallet = wallet;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Boolean getSaveCard() {
    return saveCard;
  }

  public void setSaveCard(Boolean saveCard) {
    this.saveCard = saveCard;
  }

  public PaymentNetwork getNetwork() {
    return network;
  }

  public void setNetwork(PaymentNetwork network) {
    this.network = network;
  }

  public CardType getCardType() {
    return cardType;
  }

  public void setCardType(CardType cardType) {
    this.cardType = cardType;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
