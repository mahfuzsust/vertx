package com.example.vertx.controllers;

import com.example.vertx.dao.Notification;
import com.example.vertx.dao.PaymentDao;
import com.example.vertx.dao.Status;
import com.example.vertx.dao.StatusResponse;
import com.example.vertx.models.Transaction;
import com.example.vertx.models.TransactionType;
import com.example.vertx.services.TransactionService;
import com.example.vertx.services.WalletService;
import com.example.vertx.util.NotificationUtil;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class PaymentController extends RestController {
  private TransactionService service;

  public PaymentController() {
    this.service = new TransactionService();
  }

  public void payment(RoutingContext routingContext) {
    context = routingContext;
    WalletService walletService = new WalletService();
    try {
      PaymentDao payment = Json.decodeValue(routingContext.getBodyAsString(), PaymentDao.class);
      routingContext.vertx().eventBus().send(NotificationUtil.PAYMENT_PROCESSING, new Notification(payment.getWallet(), "Started processing"));
      walletService.getById(payment.getWallet()).onSuccess(wallet -> {
        if(wallet.getBalance() >= payment.getAmount()) {
          Transaction transaction = new Transaction(null, payment.getWallet(), payment.getAmount(), TransactionType.CREDIT);
          service.add(transaction).onSuccess(unused -> {
            wallet.setBalance(wallet.getBalance() - payment.getAmount());
            walletService.update(wallet).onSuccess(unused1 -> {
              routingContext.vertx().eventBus().send(NotificationUtil.PAYMENT_COMPLETED, new Notification(payment.getWallet(), "Payment completed"));
              send(JsonObject.mapFrom(new StatusResponse(Status.SUCCESS)), 200);
            }).onFailure(this::sendFailure);
          }).onFailure(this::sendFailure);
        } else {
          send(JsonObject.mapFrom(new StatusResponse(Status.FAILED)), 200);
        }
      }).onFailure(this::sendFailure);

    } catch (Exception e) {
      e.printStackTrace();
      sendError("Invalid input", 400);
    }
  }
}
