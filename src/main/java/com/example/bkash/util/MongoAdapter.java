package com.example.bkash.util;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

public class MongoAdapter {
  private final String collection;
  private final MongoClient client;

  public MongoAdapter(String collection, MongoClient client) {
    this.collection = collection;
    this.client = client;
  }

  public Future<Boolean> delete(JsonObject query) {
    return client.removeDocument(collection, query).compose(res -> {
      if (res.getRemovedCount() == 0) {
        return Future.succeededFuture(Boolean.FALSE);
      } else {
        return Future.succeededFuture(Boolean.TRUE);
      }
    });
  }

  public Future<JsonObject> findOne(JsonObject query, JsonObject fields) {
    return client.findOne(collection, query, fields);
  }

  public Future<List<JsonObject>> find(JsonObject query) {
    return client.find(collection, query);
  }


  public Future<Boolean> update(JsonObject query, JsonObject newObject) {
    return client.replaceDocuments(collection, query, newObject).compose(res -> {
      if (res.getDocModified() == 0) {
        return Future.succeededFuture(Boolean.FALSE);
      } else {
        return Future.succeededFuture(Boolean.TRUE);
      }
    });
  }

  public Future<String> insert(JsonObject newObject) {
    return client.insert(collection, newObject);
  }

  public Future<Long> count(JsonObject query) {
    return client.count(collection, query);
  }
}
