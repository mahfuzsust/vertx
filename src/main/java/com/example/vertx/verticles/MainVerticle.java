package com.example.vertx.verticles;

import com.example.vertx.common.Log;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainVerticle extends AbstractVerticle {
  Logger logger = Log.get(MainVerticle.class);
  public static void main(final String[] args) {
    Launcher.executeCommand("run", MainVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    ConfigStoreOptions env = new ConfigStoreOptions()
      .setType("env");

    ConfigRetrieverOptions options = new ConfigRetrieverOptions()
      .addStore(env);
    ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

    retriever.getConfig(json -> {
      JsonObject mongo = json.result();
      vertx.deployVerticle(new NotificationVerticle());
      vertx.deployVerticle(new RESTVerticle(), new DeploymentOptions().setConfig(mongo));
      vertx.deployVerticle(new MessageVerticle());
    });

  }
}
