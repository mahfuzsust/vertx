package com.example.vertx.controllers;

import com.example.vertx.common.VerticleException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class RestController {
  protected RoutingContext context;
  protected void send(String message, int statusCode) {
    context.response().setStatusCode(statusCode).end(message);
  }
  protected void send(JsonObject object, int statusCode) {
    send(object.encodePrettily(), statusCode);
  }

  protected void sendFailure(Throwable throwable) {
    context.fail(new VerticleException(throwable, 500));
  }
  protected void sendError(String message, int statusCode) {
    context.fail(new VerticleException(message, statusCode));
  }
}
