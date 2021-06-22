package com.example.bkash.repository.impl;

import com.example.bkash.models.Card;
import com.example.bkash.repository.CardRepository;
import io.vertx.core.Future;

import java.util.List;

public class CardRepositoryImpl extends MongoRepository<Card, String> implements CardRepository {
  public CardRepositoryImpl() {
    super("bkash.cards");
  }

  @Override
  public Future<Boolean> exist(String key) {
    return util.exist(key);
  }

  @Override
  public Future<String> add(Card entry) {
    return util.insert(entry, entry.getId());
  }

  @Override
  public Future<Boolean> delete(String key) {
    return null;
  }

  @Override
  public Future<Boolean> update(Card entry, String key) {
    return null;
  }

  @Override
  public Future<List<Card>> getAll() {
    return null;
  }

  @Override
  public Future<Card> getById(String id) {
    return null;
  }
}
