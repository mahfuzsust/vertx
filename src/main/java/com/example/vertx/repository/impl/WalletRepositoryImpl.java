package com.example.vertx.repository.impl;

import com.example.vertx.models.Wallet;
import com.example.vertx.repository.WalletRepository;
import io.vertx.core.Future;

import java.util.List;

public class WalletRepositoryImpl extends MongoRepository<Wallet, String> implements WalletRepository {
  public WalletRepositoryImpl() {
    super("vertx.wallets");
  }

  @Override
  public Future<Boolean> exist(String key) {
    return util.exist(key);
  }

  @Override
  public Future<String> add(Wallet entry) {
    return util.insert(entry, entry.getId());
  }

  @Override
  public Future<Boolean> delete(String key) {
    return util.deleteById(key);
  }

  @Override
  public Future<Boolean> update(Wallet entry, String key) {
    return util.updateById(entry, key);
  }

  @Override
  public Future<List<Wallet>> getAll() {
    return util.getAll(Wallet.class);
  }

  @Override
  public Future<Wallet> getById(String id) {
    return util.findById(id, Wallet.class, null);
  }
  public void go() {

  }
}
