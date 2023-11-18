package com.challengers.accounts.support.containermocks;

import com.challengers.accounts.support.IntegerUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import static com.challengers.accounts.support.containermocks.ContainerImages.MOCK_SERVER_CONTAINER_IMG;
import static com.challengers.accounts.support.containermocks.TestEnvVariablesNames.MOCKSERVER_IP_ADDRESS;
import static com.challengers.accounts.support.containermocks.TestEnvVariablesNames.MOCKSERVER_PORT;
import static java.util.Objects.isNull;

public class HttpMockContainer extends GenericContainer<HttpMockContainer> {

  private static GenericContainer<HttpMockContainer> configuration;

  public static final int MOCK_SERVER_PORT = IntegerUtils.getRandomValue(1080, 5);

  private HttpMockContainer() {
    super(MOCK_SERVER_CONTAINER_IMG);
  }

  public static GenericContainer<HttpMockContainer> getInstance() {
    if (isNull(configuration)) {
      configuration = new HttpMockContainer();
      configuration.withCommand("-logLevel INFO -serverPort " + MOCK_SERVER_PORT);
      configuration.addExposedPorts(MOCK_SERVER_PORT);
      configuration.waitingFor(Wait.forListeningPort());
    }
    return configuration;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty(MOCKSERVER_IP_ADDRESS, configuration.getContainerIpAddress());
    System.setProperty(MOCKSERVER_PORT, configuration.getMappedPort(MOCK_SERVER_PORT).toString());
  }
}
