package com.challengers.accounts.usecases;

import com.challengers.accounts.gateways.AccountCacheDatabaseGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvalidAllFeaturesCache {

  private final AccountCacheDatabaseGateway accountCacheDatabaseGateway;

  public void execute() {
    final var executor = Executors.newVirtualThreadPerTaskExecutor();
    executor.submit(this::cleanCache);
    executor.shutdown();
    log.info("Final: All features caches has been deleted");
  }

  private void cleanCache() {
    accountCacheDatabaseGateway.deleteAll();
    log.info("Processing: Cleaning all features cache");
  }
}
