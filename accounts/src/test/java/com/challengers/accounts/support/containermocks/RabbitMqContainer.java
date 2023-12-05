package com.challengers.accounts.support.containermocks;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.RabbitMQContainer;

@Slf4j
public class RabbitMqContainer extends RabbitMQContainer {

  private static final String VIRTUAL_HOST = "vHostAppBase";
  private static RabbitMqContainer container;

  private RabbitMqContainer() {
    super();
    withVhost(VIRTUAL_HOST);
  }

  public static RabbitMqContainer getInstance() {
    if (container == null) {
      container = new RabbitMqContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("RABBITMQ_HOST", container.getHost());
    System.setProperty("RABBITMQ_PORT", container.getAmqpPort().toString());
    System.setProperty("RABBITMQ_USER", container.getAdminUsername());
    System.setProperty("RABBITMQ_PASSWORD", container.getAdminPassword());
    System.setProperty("RABBITMQ_VHOST", VIRTUAL_HOST);
  }

  @Override
  public void stop() {
    log.warn("Do nothing, JVM handles shut down");
  }
}
