package com.example.bkash.verticles;

import com.example.bkash.common.Log;
import io.vertx.core.*;
import org.apache.log4j.Logger;

public class MainVerticle extends AbstractVerticle {
  Logger logger = Log.get(MainVerticle.class);
  public static void main(final String[] args) {
    Launcher.executeCommand("run", MainVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new MongoVerticle());
    vertx.deployVerticle(new NotificationVerticle());
    vertx.deployVerticle(new RESTVerticle());
    vertx.deployVerticle(new MessageVerticle());
  }
}
