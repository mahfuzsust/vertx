package com.example.bkash.util;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoConnection {
  private static MongoClient cli = null;

  public MongoConnection(Vertx vertx, JsonObject conf) {
    JsonObject opt = new JsonObject();
    String h = conf.getString("mongo_host", "localhost");
    if (!h.isEmpty()) {
      opt.put("host", h);
    }
    String p = conf.getString("mongo_port", "27017");
    if (!p.isEmpty()) {
      opt.put("port", Integer.parseInt(p));
    }
    String dbName = conf.getString("mongo_db_name", "bkash");
    System.out.println("+++++++++++++++++++++++++++" + dbName);
    if (!dbName.isEmpty()) {
      opt.put("db_name", dbName);
    }
    cli = MongoClient.createShared(vertx, opt);
  }

  public static MongoClient getClient() {
    return cli;
  }
}
