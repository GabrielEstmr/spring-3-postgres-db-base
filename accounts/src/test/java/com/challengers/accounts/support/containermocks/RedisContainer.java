package com.challengers.accounts.support.containermocks;

import org.testcontainers.containers.GenericContainer;

public class RedisContainer extends GenericContainer<RedisContainer> {

  private static final int REDIS_PORT = 6379;

  public RedisContainer(final String redisVersion) {
    super("redis:" + redisVersion);
    withExposedPorts(REDIS_PORT);
  }
}
