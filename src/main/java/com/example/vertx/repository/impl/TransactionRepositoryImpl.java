package com.example.vertx.repository.impl;

import com.example.vertx.models.Transaction;
import com.example.vertx.repository.TransactionRepository;
import io.vertx.core.Future;

import java.util.List;

public class TransactionRepositoryImpl extends MongoRepository<Transaction, String> implements TransactionRepository {
  public TransactionRepositoryImpl() {
    super("vertx.transactions");
  }

  @Override
  public Future<Boolean> exist(String key) {
    return util.exist(key);
  }

  @Override
  public Future<String> add(Transaction entry) {
    return util.insert(entry, entry.getId());
  }

  @Override
  public Future<Boolean> delete(String key) {
    throw null;
  }

  @Override
  public Future<Boolean> update(Transaction entry, String key) {
    return null;
  }

  @Override
  public Future<List<Transaction>> getAll() {
    return null;
  }

  @Override
  public Future<Transaction> getById(String id) {
    return null;
  }
}
