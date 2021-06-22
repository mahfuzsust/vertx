package com.example.bkash.services;

import com.example.bkash.models.Transaction;
import com.example.bkash.repository.TransactionRepository;
import com.example.bkash.repository.impl.TransactionRepositoryImpl;
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
