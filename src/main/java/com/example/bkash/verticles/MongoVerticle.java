package com.example.bkash.verticles;

import com.example.bkash.common.Log;
import com.example.bkash.util.MongoConnection;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.apache.log4j.Logger;

public class MongoVerticle extends AbstractVerticle {
  Logger logger = Log.get(MongoVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    new MongoConnection(vertx, config());
    MongoClient client = MongoConnection.getClient();
    client.runCommand("ping", new JsonObject().put("ping", 1))
      .onFailure(event -> {
        logger.error("MongoDB connection error");
        startPromise.fail(event.getCause());
      })
      .onSuccess(event -> logger.info("MongoDB connected"));
  }
}
