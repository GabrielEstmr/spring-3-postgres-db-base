package com.challengers.accounts.support.containermocks;

import org.testcontainers.containers.GenericContainer;

import static com.challengers.accounts.support.containermocks.ContainerImages.REDIS_CONTAINER_IMG;
import static com.challengers.accounts.support.containermocks.TestEnvVariablesNames.REDIS_HOST;
import static com.challengers.accounts.support.containermocks.TestEnvVariablesNames.REDIS_PORT;
import static java.util.Objects.isNull;

public class RedisContainer extends GenericContainer<RedisContainer> {

  private static final int REDIS_PORT_VALUE = 6379;

  private static RedisContainer container;

  private RedisContainer() {
    super(REDIS_CONTAINER_IMG);
  }

  public static RedisContainer getInstance() {
    if (isNull(container)) {
      container = new RedisContainer();
      container.withExposedPorts(REDIS_PORT_VALUE);
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty(REDIS_HOST, container.getContainerIpAddress());
    System.setProperty(REDIS_PORT, container.getFirstMappedPort().toString());
  }
}
