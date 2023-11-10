package com.challengers.accounts.support;

import com.challengers.accounts.AccountsApplication;
import com.challengers.accounts.support.containermocks.MockServerContainerConfiguration;
import com.challengers.accounts.support.containermocks.PostgresContainerConfiguration;
import com.challengers.accounts.support.containermocks.RedisContainerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.function.Predicate;

import static java.lang.Long.min;
import static java.lang.Thread.sleep;

@Slf4j
@ActiveProfiles("test-container")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AccountsApplication.class
    )
@ExtendWith(SpringExtension.class)
public class TestContainerSupport extends TestSupport {

  public static final PostgresContainerConfiguration postgresContainer;
  public static final RedisContainerConfiguration redisContainerConfiguration;
  public static final MockServerContainerConfiguration mockServerContainer;

  protected static final String TRANSACTION_CONTROLLER_REQUEST_RESOURCE_DIR =
      "/json/controllers/requests/transaction";
  protected static final String ACCOUNT_CONTROLLER_REQUEST_RESOURCE_DIR =
      "/json/controllers/requests/account";
  protected static final String TRANSACTION_CONTROLLER_RESPONSES_RESOURCE_DIR =
      "/json/controllers/responses/transaction";
  protected static final String ACCOUNT_CONTROLLER_RESPONSES_RESOURCE_DIR =
      "/json/controllers/responses/account";

  static {
    postgresContainer = PostgresContainerConfiguration.getInstance();
    postgresContainer.withDatabaseName(postgresContainer.getApplicationDBName());
    postgresContainer.start();
    redisContainerConfiguration = RedisContainerConfiguration.getInstance();
    redisContainerConfiguration.start();
    mockServerContainer = MockServerContainerConfiguration.getInstance();
    mockServerContainer.start();
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
