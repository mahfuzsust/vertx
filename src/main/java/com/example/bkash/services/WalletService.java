package com.example.bkash.services;

import com.example.bkash.models.Wallet;
import com.example.bkash.repository.WalletRepository;
import com.example.bkash.repository.impl.WalletRepositoryImpl;
import io.vertx.core.Future;


public class WalletService {
  private WalletRepository repository;

  public WalletService() {
    this.repository = new WalletRepositoryImpl();
  }

  public Future<String> add(Wallet obj) {
    return this.repository.add(obj);
  }

  public Future<Wallet> getById(String id) {
    return this.repository.getById(id);
  }

  public Future<Boolean> update(Wallet obj) {
    return this.repository.update(obj, obj.getId());
  }

  public Future<Boolean> exist(String id) {
    return repository.exist(id);
  }
}
