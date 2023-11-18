package com.challengers.accounts.support.containermocks;

import org.testcontainers.containers.PostgreSQLContainer;

import static com.challengers.accounts.support.containermocks.ContainerImages.POSTGRES_DB_CONTAINER_IMG;
import static com.challengers.accounts.support.containermocks.TestEnvVariablesNames.*;
import static java.util.Objects.isNull;

public class PostgresContainer extends PostgreSQLContainer {

  public static final String POSTGRES_DB_NAME = "app_account_challenger";
  private static PostgresContainer container;

  public PostgresContainer() {
    super(POSTGRES_DB_CONTAINER_IMG);
  }

  public static PostgresContainer getInstance() {
    if (isNull(container)) {
      container = new PostgresContainer();
      container.withDatabaseName(POSTGRES_DB_NAME);
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty(POSTGRES_URI, container.getJdbcUrl());
    System.setProperty(POSTGRES_USER, container.getUsername());
    System.setProperty(POSTGRES_PASSWORD, container.getPassword());
  }
}
