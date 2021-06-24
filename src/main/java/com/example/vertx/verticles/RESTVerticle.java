package com.example.vertx.verticles;

import com.example.vertx.common.Log;
import com.example.vertx.common.VerticleException;
import com.example.vertx.controllers.FundController;
import com.example.vertx.controllers.PaymentController;
import com.example.vertx.controllers.WalletController;
import com.example.vertx.util.MongoConnection;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.apache.log4j.Logger;


public class RESTVerticle extends AbstractVerticle {
  Logger logger = Log.get(RESTVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    new MongoConnection(vertx, config());
    HealthCheckHandler healthCheckHandler = HealthCheckHandler.create(vertx);
    WalletController walletController = new WalletController();
    PaymentController paymentController = new PaymentController();
    FundController fundController = new FundController();
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.route().handler(ctx -> {
      ctx.response()
        .putHeader("content-type", "application/json; charset=utf-8");
      ctx.next();
    });

    router.route().failureHandler(ctx -> {
      VerticleException exception = (VerticleException)ctx.failure();
      final JsonObject error = new JsonObject()
        .put("timestamp", System.nanoTime())
        .put("status", exception.getStatusCode())
        .put("error", HttpResponseStatus.valueOf(exception.getStatusCode()).reasonPhrase())
        .put("path", ctx.normalisedPath());
      if(exception.getMessage() != null) {
        error.put("message", exception.getMessage());
      }
      ctx.response().putHeader("content-type", "application/json; charset=utf-8");
      ctx.response().setStatusCode(exception.getStatusCode());
      ctx.response().end(error.encode());
    });
    router.get("/health").handler(healthCheckHandler);
    router.post("/core/api/balance").handler(walletController::getBalance);
    router.post("/core/api/wallet/add").handler(walletController::addWallet);
    router.post("/core/api/payment").handler(paymentController::payment);
    router.post("/core/api/fund/add").handler(fundController::addFund);

    router.route().handler(ctx -> ctx.fail(new VerticleException("Not found" , 404)));

    Integer port = config().getJsonObject("http").getInteger("port", 8080);

    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(port, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          logger.info("HTTP server started on port " + port);
        } else {
          logger.error("Error " + http.cause());
          startPromise.fail(http.cause());
        }
      });
  }

}
