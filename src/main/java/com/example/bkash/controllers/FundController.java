package com.example.bkash.controllers;

import com.example.bkash.dao.FundDao;
import com.example.bkash.dao.Notification;
import com.example.bkash.dao.Status;
import com.example.bkash.dao.StatusResponse;
import com.example.bkash.models.Card;
import com.example.bkash.models.Transaction;
import com.example.bkash.models.TransactionType;
import com.example.bkash.services.CardService;
import com.example.bkash.services.TransactionService;
import com.example.bkash.services.WalletService;
import com.example.bkash.util.NotificationUtil;
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
