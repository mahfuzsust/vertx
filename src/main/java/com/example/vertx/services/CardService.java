package com.example.vertx.services;

import com.example.vertx.models.Card;
import com.example.vertx.repository.CardRepository;
import com.example.vertx.repository.impl.CardRepositoryImpl;
import io.vertx.core.Future;

public class CardService {
  private CardRepository repository;

  public CardService() {
    repository = new CardRepositoryImpl();
  }

  public Future<String> add(Card card) {
    return repository.add(card);
  }

  public Future<Boolean> exist(String id) {return repository.exist(id); }
}
