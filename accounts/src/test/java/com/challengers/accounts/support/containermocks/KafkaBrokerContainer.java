package com.challengers.accounts.support.containermocks;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static com.challengers.accounts.support.containermocks.ContainerImages.KAFKA_CONTAINER_IMG;

@Slf4j
public class KafkaBrokerContainer extends KafkaContainer {

  private static KafkaBrokerContainer container;

  private KafkaBrokerContainer() {
    super(DockerImageName.parse(KAFKA_CONTAINER_IMG));
  }

  public static KafkaBrokerContainer getInstance() {
    if (container == null) {
      container = new KafkaBrokerContainer();
    }
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("KAFKA_BOOTSTRAP_SERVERS", container.getBootstrapServers());
  }

  @Override
  public void stop() {
    log.warn("Do nothing, JVM handles shut down");
  }
}
