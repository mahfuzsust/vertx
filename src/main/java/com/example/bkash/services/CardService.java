package com.example.bkash.services;

import com.example.bkash.models.Card;
import com.example.bkash.repository.CardRepository;
import com.example.bkash.repository.impl.CardRepositoryImpl;
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
