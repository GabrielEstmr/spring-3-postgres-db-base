package com.challengers.accounts.support.containermocks;

public class RedisContainerConfiguration extends RedisContainer {

  private static final String REDIS_VERSION = "alpine";
  private static RedisContainerConfiguration container;

  public RedisContainerConfiguration() {
    super(REDIS_VERSION);
  }

  public static RedisContainerConfiguration getInstance() {
    if (container == null) {
      container = new RedisContainerConfiguration();
    }

    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("REDIS_HOST", container.getContainerIpAddress());
    System.setProperty("REDIS_PORT", container.getFirstMappedPort().toString());
  }
}
