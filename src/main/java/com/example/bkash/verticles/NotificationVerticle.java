package com.example.bkash.verticles;

import com.example.bkash.codecs.NotificationCodec;
import com.example.bkash.common.Log;
import com.example.bkash.dao.Notification;
import com.example.bkash.util.NotificationUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import org.apache.log4j.Logger;

public class NotificationVerticle extends AbstractVerticle {
  private Logger logger = Log.get(NotificationVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.eventBus().registerDefaultCodec(Notification.class, new NotificationCodec());
    vertx.eventBus().consumer(NotificationUtil.PAYMENT_PROCESSING, this::logNotification);
    vertx.eventBus().consumer(NotificationUtil.PAYMENT_COMPLETED, this::logNotification);
    vertx.eventBus().consumer(NotificationUtil.FUND_ADD_SUCCESS, this::logNotification);
    vertx.eventBus().consumer(NotificationUtil.FUND_ADD_FAILED, this::logNotification);
  }

  private <T> void logNotification(Message<T> message) {
    logger.info(message.body());
  }
}
