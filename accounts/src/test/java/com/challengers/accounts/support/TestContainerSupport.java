package com.challengers.accounts.support;

import com.challengers.accounts.AccountsApplication;
import com.challengers.accounts.support.containermocks.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;

import java.util.function.Predicate;

import static java.lang.Long.min;
import static java.lang.Thread.sleep;

@Slf4j
@ActiveProfiles("test-container")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AccountsApplication.class)
@ExtendWith(SpringExtension.class)
public class TestContainerSupport extends TestSupport {

  public static final PostgresContainer POSTGRES_DB_CONTAINER;
  public static final RedisContainer REDIS_CONTAINER;
  public static final GenericContainer<HttpMockContainer> HTTP_MOCK_CONTAINER;

  protected static final String TRANSACTION_CONTROLLER_REQUEST_RESOURCE_DIR =
      "/json/controllers/requests/transaction";
  protected static final String ACCOUNT_CONTROLLER_REQUEST_RESOURCE_DIR =
      "/json/controllers/requests/account";
  protected static final String TRANSACTION_CONTROLLER_RESPONSES_RESOURCE_DIR =
      "/json/controllers/responses/transaction";
  protected static final String ACCOUNT_CONTROLLER_RESPONSES_RESOURCE_DIR =
      "/json/controllers/responses/account";

  static {
    POSTGRES_DB_CONTAINER = PostgresContainer.getInstance();
    POSTGRES_DB_CONTAINER.start();
    REDIS_CONTAINER = RedisContainer.getInstance();
    REDIS_CONTAINER.start();
    HTTP_MOCK_CONTAINER = HttpMockContainer.getInstance();
    HTTP_MOCK_CONTAINER.start();
  }

  protected void await(final Long millis) {
    try {
      sleep(millis);
    } catch (InterruptedException e) {
      log.error("Thread sleeping has been failed", e);
    }
  }

  protected <T> void waitUntil(final Integer limitCount, final T id, final Predicate<T> predicate) {
    long increment = 500;
    int count = 0;

    try {
      do {
        count++;
        await(min(increment * count, 5000));
      } while (!predicate.test(id) && count < limitCount);
    } catch (Exception e) {
      log.error("Fail to wait until condition", e);
    }
  }
}
