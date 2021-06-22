package com.example.vertx.util;

import com.mongodb.lang.Nullable;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MongoAdapterUtil<T, I> extends MongoAdapter {

  public MongoAdapterUtil(String collection, MongoClient client) {
    super(collection, client);
  }

  public Future<Boolean> delete(Map<String, String> map) {
    JsonObject query = new JsonObject();
    map.keySet().forEach(s -> query.put(s, map.get(s)));
    return super.delete(query);
  }

  public Future<Boolean> deleteById(I id) {
    Map<String, String> map = new HashMap<>();
    map.put("_id", id.toString());
    return delete(map);
  }

  public Future<Boolean> exist(Map<String, String> map) {
    JsonObject query = new JsonObject();
    map.keySet().forEach(s -> query.put(s, map.get(s)));
    return count(query).compose(count -> Future.succeededFuture(count > 0));
  }

  public Future<Boolean> exist(I id) {
    Map<String, String> map = new HashMap<>();
    map.put("_id", id.toString());
    return exist(map);
  }

  public Future<T> findById(I id, Class<T> clazz, @Nullable List<String> fields) {
    Map<String, String> map = new HashMap<>();
    map.put("_id", id.toString());
    return findBy(map, clazz, fields);
  }

  public Future<T> findBy(Map<String, String> map, Class<T> clazz, @Nullable List<String> fields) {
    JsonObject jsonObject = new JsonObject();
    map.keySet().forEach(s -> jsonObject.put(s, map.get(s)));
    JsonObject fieldsObject = null;
    if(fields != null) {
      JsonObject finalFieldsObject = fieldsObject;
      fields.forEach(s -> finalFieldsObject.put(s, ""));
      fieldsObject = finalFieldsObject;
    }
    return super.findOne(jsonObject, fieldsObject).compose(entry -> {
      entry.put("id", entry.getValue("_id")).remove("_id");
      T val = Json.decodeValue(entry.encode(), clazz);
      return Future.succeededFuture(val);
    });
  }

  public Future<Boolean> updateBy(T entity, Map<String, String> queryMap) {
    JsonObject queryObject = new JsonObject();
    queryMap.keySet().forEach(s -> queryObject.put(s, queryMap.get(s)));
    JsonObject document = new JsonObject(Json.encodePrettily(entity));
    return super.update(queryObject, document);
  }

  public Future<Boolean> updateById(T entity, I id) {
    Map<String, String> queryMap = new HashMap<>();
    queryMap.put("_id", id.toString());
    return updateBy(entity, queryMap);
  }


  public Future<String> insert(T entity, I id) {
    String s = Json.encodePrettily(entity);
    JsonObject document = new JsonObject(s);
    if(id != null) document.put("_id", id.toString());
    return super.insert(document);
  }

  public Future<List<T>> getAll(Class<T> clazz) {
    JsonObject jq = new JsonObject("{}");
    return super.find(jq).compose(resl -> {
      List<T> ml = new LinkedList<>();
      for (JsonObject jo : resl) {
        T env = Json.decodeValue(jo.encode(), clazz);
        ml.add(env);
      }
      return Future.succeededFuture(ml);
    });
  }
}
