package com.challengers.accounts.support.containermocks;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public class MockServerContainer extends GenericContainer<MockServerContainer> {

  public static final int MOCK_SERVER_PORT = 1080;

  public MockServerContainer(String version) {
    super("jamesdbloom/mockserver:" + version);
    withCommand("-logLevel INFO -serverPort " + MOCK_SERVER_PORT);
    addExposedPorts(MOCK_SERVER_PORT);
    waitingFor(Wait.forListeningPort());
  }
}
