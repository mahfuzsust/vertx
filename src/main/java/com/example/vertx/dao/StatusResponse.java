package com.example.vertx.dao;

public class StatusResponse {
  private Status status;

  public StatusResponse() {
  }

  public StatusResponse(Status status) {
    this.status = status;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
