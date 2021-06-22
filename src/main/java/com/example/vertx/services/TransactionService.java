package com.example.vertx.services;

import com.example.vertx.models.Transaction;
import com.example.vertx.repository.TransactionRepository;
import com.example.vertx.repository.impl.TransactionRepositoryImpl;
import io.vertx.core.Future;

public class TransactionService {
  private TransactionRepository repository;

  public TransactionService() {
    repository = new TransactionRepositoryImpl();
  }

  public Future<String> add(Transaction transaction) {
    return repository.add(transaction);
  }
}
