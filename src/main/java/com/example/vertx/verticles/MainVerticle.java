package com.example.vertx.verticles;

import com.example.vertx.common.Log;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

public class MainVerticle extends AbstractVerticle {
  private static final String CONFIG_PATH = "config.json";
  Logger logger = Log.get(MainVerticle.class);
  public static void main(final String[] args) {
    Launcher.executeCommand("run", MainVerticle.class.getName());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    getConfig().onSuccess(config -> {
      logger.info("Config successfully loaded");
      JsonObject applicationConfig = config.getJsonObject("application");
      vertx.deployVerticle(new NotificationVerticle());
      vertx.deployVerticle(new RESTVerticle(), new DeploymentOptions().setConfig(applicationConfig));
      vertx.deployVerticle(new MessageVerticle());

      DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(applicationConfig);
      CompositeFuture.all(
        deployVerticle(NotificationVerticle.class, deploymentOptions),
        deployVerticle(RESTVerticle.class, deploymentOptions),
        deployVerticle(MessageVerticle.class, deploymentOptions))
        .onSuccess(compositeFuture -> {
          startPromise.complete();
        }).onFailure(startPromise::fail);
    }).onFailure(startPromise::fail);
  }
  private Future<Void> deployVerticle(Class<? extends Verticle> clz, DeploymentOptions deploymentOptions){
    Promise<Void> deploymentFuture = Promise.promise();
    vertx.deployVerticle(clz, deploymentOptions, ar ->{
      if (ar.succeeded()) {
        deploymentFuture.complete();
      } else {
        deploymentFuture.fail(ar.cause());
      }
    });
    return deploymentFuture.future();
  }

  private Future<JsonObject> getConfig() {
    ConfigStoreOptions env = new ConfigStoreOptions()
      .setType("env");

    ConfigStoreOptions defaultConfig = new ConfigStoreOptions()
      .setType("file")
      .setFormat("json")
      .setConfig(new JsonObject().put("path", CONFIG_PATH));

    ConfigRetrieverOptions options = new ConfigRetrieverOptions()
      .addStore(defaultConfig).addStore(env);
    ConfigRetriever retriever = ConfigRetriever.create(vertx, options);

    return retriever.getConfig();
  }
}
