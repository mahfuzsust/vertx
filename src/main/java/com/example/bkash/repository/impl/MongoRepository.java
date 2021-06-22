package com.example.bkash.repository.impl;

import com.example.bkash.util.MongoConnection;
import com.example.bkash.util.MongoAdapterUtil;

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
