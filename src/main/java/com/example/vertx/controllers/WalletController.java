package com.example.vertx.controllers;

import com.example.vertx.common.Log;
import com.example.vertx.dao.Status;
import com.example.vertx.dao.StatusResponse;
import com.example.vertx.dao.WalletBalance;
import com.example.vertx.models.Wallet;
import com.example.vertx.services.WalletService;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

public class WalletController extends RestController {
  Logger logger = Log.get(WalletController.class);
  private WalletService service;

  public WalletController() {
    this.service = new WalletService();
  }

  public void getBalance(RoutingContext routingContext) {
    this.context = routingContext;
    logger.info("Get balance request");
    final WalletBalance walletBalance = Json.decodeValue(routingContext.getBodyAsString(), WalletBalance.class);
    service.getById(walletBalance.getWallet()).onSuccess(wallet -> send(JsonObject.mapFrom(wallet), 200)).onFailure(this::sendFailure);
  }

  public void addWallet(RoutingContext routingContext) {
    this.context = routingContext;
    WalletBalance walletBalance = null;
    try {
      walletBalance = Json.decodeValue(routingContext.getBodyAsString(), WalletBalance.class);
    } catch (Exception e) {
      sendError("Invalid input", 400);
    }
    assert walletBalance != null;
    final Wallet wallet = new Wallet(walletBalance.getWallet(), 0.0);

    service.exist(wallet.getId()).onSuccess(exist -> {
      if(exist) {
        sendError("Already exist", 400);
      } else {
        service.add(wallet).onFailure(this::sendFailure).onSuccess(s -> send(JsonObject.mapFrom(new StatusResponse(Status.SUCCESS)), 201));
      }

    }).onFailure(this::sendFailure);


  }
}
