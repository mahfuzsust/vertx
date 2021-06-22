package com.example.bkash;

import com.example.bkash.util.MongoConnection;
import com.example.bkash.verticles.NotificationVerticle;
import com.example.bkash.verticles.RESTVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@ExtendWith(VertxExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRestVerticle {
  Vertx vertx = Vertx.vertx();

  static String deploymentId;
  static String wallet;

  @BeforeAll
  static void setUp(Vertx vertx, VertxTestContext testContext) {
    DeploymentOptions options = new DeploymentOptions()
      .setConfig(new JsonObject().put("http.port", 8080).put("mongo_db_name", "bkash_test"));
    vertx.deployVerticle(new RESTVerticle(), options, testContext.succeeding(id -> {
      vertx.deployVerticle(new NotificationVerticle());
      deploymentId = id;
      wallet = UUID.randomUUID().toString();
      CompositeFuture.all(MongoConnection.getClient().dropCollection("bkash.wallets"),
        MongoConnection.getClient().dropCollection("bkash.transactions"),
        MongoConnection.getClient().dropCollection("bkash.cards")).onSuccess(compositeFuture -> {
        testContext.completeNow();
      }).onFailure(throwable -> {
        testContext.failNow(throwable);
      });
    }));
  }

  @AfterAll
  static void tearDown(Vertx vertx, VertxTestContext testContext) {
    testContext
      .assertComplete(vertx.undeploy(deploymentId))
      .onComplete(ar -> testContext.completeNow());
  }

  private WebClient getWebClient() {
    WebClientOptions opts = new WebClientOptions()
      .setDefaultPort(8080)
      .setDefaultHost("localhost");
    return WebClient.create(vertx, opts);
  }

  @Test
  public void testHealth(VertxTestContext testContext) {
    getWebClient().get("/health").send().onSuccess(response -> testContext.verify(() -> {
      Assertions.assertEquals(204, response.statusCode());
      testContext.completeNow();
    }));
  }

  @Test
  public void testWalletAddInvalidInput(VertxTestContext testContext) {
    getWebClient()
      .post("/core/api/wallet/add")
      .sendJsonObject(new JsonObject().put("walletx", wallet))
      .onSuccess(response -> testContext.verify(() -> {
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Invalid input", response.body().toJsonObject().getString("message"));

        testContext.completeNow();
      }));
  }

  @Test
  @Order(1)
  public void testWalletAdd(VertxTestContext testContext) {
    getWebClient()
      .post("/core/api/wallet/add")
      .sendJsonObject(new JsonObject().put("wallet", wallet))
      .onSuccess(response -> testContext.verify(() -> {
        Assertions.assertEquals(201, response.statusCode());
        testContext.completeNow();
      }));
  }

  @Test
  @Order(2)
  public void testWalletAddFailure(VertxTestContext testContext) {
    getWebClient()
      .post("/core/api/wallet/add")
      .sendJsonObject(new JsonObject().put("wallet", wallet))
      .onSuccess(response -> testContext.verify(() -> {
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("Already exist", response.body().toJsonObject().getString("message"));
        testContext.completeNow();
      }));
  }

  @Test
  @Order(2)
  public void testGetBalance(VertxTestContext testContext) {
    JsonObject wallet = new JsonObject().put("wallet", TestRestVerticle.wallet);
    getWebClient()
      .post("/core/api/balance")
      .sendJsonObject(wallet,
        testContext.succeeding(response -> testContext.verify(() -> {
          Assertions.assertEquals(200, response.statusCode());
          Assertions.assertEquals("0.0", response.body().toJsonObject().getString("balance"));
          testContext.completeNow();
        })));
  }
  @Test
  @Order(2)
  public void testPayment(VertxTestContext testContext) {
    JsonObject request = new JsonObject()
      .put("wallet", TestRestVerticle.wallet)
      .put("amount", "200")
      .put("callbackUrl", "localhost:6666");
    getWebClient()
      .post("/core/api/payment")
      .sendJsonObject(request,
        testContext.succeeding(response -> testContext.verify(() -> {
          Assertions.assertEquals(200, response.statusCode());
          Assertions.assertEquals("failed", response.body().toJsonObject().getString("status"));
          testContext.completeNow();
        })));
  }

  @Test
  @Order(3)
  public void testFund(VertxTestContext testContext) {
    JsonObject request = new JsonObject()
      .put("wallet", TestRestVerticle.wallet)
      .put("amount", "200")
      .put("cardNumber", UUID.randomUUID().toString())
      .put("saveCard", "false")
      .put("network", "stripe")
      .put("cardType", "visa")
      .put("status", "success");

    getWebClient()
      .post("/core/api/fund/add")
      .sendJsonObject(request,
        testContext.succeeding(response -> testContext.verify(() -> {
          Assertions.assertEquals(200, response.statusCode());
          Assertions.assertEquals("success", response.body().toJsonObject().getString("status"));
          testContext.completeNow();
        })));
  }

  @Test
  @Order(3)
  public void testFundFailed(VertxTestContext testContext) {
    JsonObject request = new JsonObject()
      .put("wallet", TestRestVerticle.wallet)
      .put("status", "failed");

    getWebClient()
      .post("/core/api/fund/add")
      .sendJsonObject(request,
        testContext.succeeding(response -> testContext.verify(() -> {
          Assertions.assertEquals(200, response.statusCode());
          Assertions.assertEquals("failed", response.body().toJsonObject().getString("status"));
          testContext.completeNow();
        })));
  }

  @Test
  @Order(4)
  public void testGetBalanceAfterFundAdd(VertxTestContext testContext) {
    JsonObject wallet = new JsonObject().put("wallet", TestRestVerticle.wallet);
    getWebClient()
      .post("/core/api/balance")
      .sendJsonObject(wallet,
        testContext.succeeding(response -> testContext.verify(() -> {
          Assertions.assertEquals(200, response.statusCode());
          Assertions.assertEquals("200.0", response.body().toJsonObject().getString("balance"));
          testContext.completeNow();
        })));
  }
  @Test
  @Order(5)
  public void testPaymentSuccess(VertxTestContext testContext) {
    JsonObject request = new JsonObject()
      .put("wallet", TestRestVerticle.wallet)
      .put("amount", "200")
      .put("callbackUrl", "localhost:6666");
    getWebClient()
      .post("/core/api/payment")
      .sendJsonObject(request,
        testContext.succeeding(response -> testContext.verify(() -> {
          Assertions.assertEquals(200, response.statusCode());
          Assertions.assertEquals("success", response.body().toJsonObject().getString("status"));
          testContext.completeNow();
        })));
  }
}
