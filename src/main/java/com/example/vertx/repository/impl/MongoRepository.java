package com.example.vertx.repository.impl;

import com.example.vertx.util.MongoConnection;
import com.example.vertx.util.MongoAdapterUtil;

public class MongoRepository<T, I> {
  protected final MongoAdapterUtil<T, I> util;
  protected final String COLLECTION;

  public MongoRepository() {
    this("");
  }

  public MongoRepository(String collection) {
    this.COLLECTION = collection;
    this.util = new MongoAdapterUtil<>(COLLECTION, MongoConnection.getClient());
  }
}
