package com.challengers.accounts.support.containermocks;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainerConfiguration extends PostgreSQLContainer {

  private static final String DOCKER_IMAGE_NAME = "postgres:16-alpine";
  public static final String POSTGRES_DB_NAME = "app_account_challenger";
  private static PostgresContainerConfiguration container;

  public PostgresContainerConfiguration() {
    super(DOCKER_IMAGE_NAME);
  }

  public String getApplicationDBName() {
    return POSTGRES_DB_NAME;
  }

  public static PostgresContainerConfiguration getInstance() {

    if (container == null) {
      container = new PostgresContainerConfiguration();
    }

    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("POSTGRES_URI", container.getJdbcUrl());
    System.setProperty("POSTGRES_USER", container.getUsername());
    System.setProperty("POSTGRES_PASSWORD", container.getPassword());
  }
}
