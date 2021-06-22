package com.example.vertx.repository;

import io.vertx.core.Future;

import java.util.List;

public interface Repository<T, I> {
  Future<Boolean> exist(I key);

  Future<String> add(T entry);

  Future<Boolean> delete(I key);

  Future<Boolean> update(T entry, I key);

  Future<List<T>> getAll();

  Future<T> getById(I id);
}
