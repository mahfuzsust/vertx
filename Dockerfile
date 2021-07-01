FROM maven:3.8.1-jdk-8-slim as builder
WORKDIR /project

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src src/
RUN mvn package -DskipTests -B

FROM vertx/vertx4

ENV VERTICLE_NAME com.example.vertx.verticles.MainVerticle

ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080

COPY --from=builder /project/target/*-fat.jar $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c","java -jar *-fat.jar"]
