package com.challengers.accounts.usecases;

import com.challengers.accounts.gateways.FeatureToggleGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvalidAllGatewayCaches {

  private final FeatureToggleGateway featureToggleGateway;

  public void execute() {
    final var executor = Executors.newVirtualThreadPerTaskExecutor();
    executor.submit(this::cleanCache);
    executor.shutdown();
    log.info("Final: All caches has been deleted");
  }

  private void cleanCache() {
    featureToggleGateway.clearCache();
    log.info("Processing: Cleaning all gateways cache");
  }
}
