package com.example.vertx.controllers;

import com.example.vertx.dao.FundDao;
import com.example.vertx.dao.Notification;
import com.example.vertx.dao.Status;
import com.example.vertx.dao.StatusResponse;
import com.example.vertx.models.Card;
import com.example.vertx.models.Transaction;
import com.example.vertx.models.TransactionType;
import com.example.vertx.services.CardService;
import com.example.vertx.services.TransactionService;
import com.example.vertx.services.WalletService;
import com.example.vertx.util.NotificationUtil;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class FundController extends RestController {
  private TransactionService service;

  public FundController() {
    this.service = new TransactionService();
  }

  public void addFund(RoutingContext routingContext) {
    context = routingContext;
    WalletService walletService = new WalletService();
    CardService cardService = new CardService();
    try {
      FundDao fundDao = Json.decodeValue(routingContext.getBodyAsString(), FundDao.class);
      if (fundDao.getStatus() == Status.FAILED) {
        routingContext.vertx().eventBus().send(NotificationUtil.FUND_ADD_FAILED, new Notification(fundDao.getWallet(), "Add money failed"));
        send(JsonObject.mapFrom(new StatusResponse(Status.FAILED)), 200);
        return;
      }

      walletService.getById(fundDao.getWallet()).onSuccess(wallet -> {
        Transaction transaction = new Transaction(null, fundDao.getWallet(), fundDao.getAmount(), TransactionType.DEBIT);
        service.add(transaction).onSuccess(transactionAddResponse -> {
          wallet.setBalance(wallet.getBalance() + fundDao.getAmount());
          if(fundDao.getSaveCard()) {
            Card card = new Card(null, wallet.getId(), fundDao.getCardNumber(), fundDao.getCardType(), fundDao.getNetwork());
            cardService.add(card);
          }
          walletService.update(wallet).onSuccess(walletAddResponse -> {
            if (walletAddResponse) {
              routingContext.vertx().eventBus().send(NotificationUtil.FUND_ADD_SUCCESS, new Notification(fundDao.getWallet(), "Fund added"));
              send(JsonObject.mapFrom(new StatusResponse(Status.SUCCESS)), 200);
            }
          });
        }).onFailure(this::sendFailure);
      }).onFailure(this::sendFailure);

    } catch (Exception e) {
      sendError("Invalid input", 400);
    }
  }
}
