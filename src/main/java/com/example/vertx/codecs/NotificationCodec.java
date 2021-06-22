package com.example.vertx.codecs;

import com.example.vertx.dao.Notification;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class NotificationCodec implements MessageCodec<Notification, Notification> {
  @Override
  public void encodeToWire(Buffer buffer, Notification notification) {

  }

  @Override
  public Notification decodeFromWire(int i, Buffer buffer) {
    return null;
  }

  @Override
  public Notification transform(Notification notification) {
    return notification;
  }

  @Override
  public String name() {
    return "NotificationCodec";
  }

  @Override
  public byte systemCodecID() {
    return -1;
  }
}
