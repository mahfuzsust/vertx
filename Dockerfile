FROM vertx/vertx4

ENV VERTICLE_NAME com.example.vertx.verticles.MainVerticle
ENV VERTICLE_FILE target/vertx-1.0.0-SNAPSHOT-fat.jar

ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080

COPY $VERTICLE_FILE $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]
