package com.challengers.accounts.configs.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.*;

@Configuration
public class KafkaConfiguration {

  @Primary
  @Bean("defaultKafkaProperties")
  @ConfigurationProperties(prefix = "spring.kafka.default-config")
  public KafkaProperties defaultKafkaProperties() {
    return new KafkaProperties();
  }

  @Primary
  @Bean("defaultKafkaTemplate")
  public KafkaTemplate<String, String> defaultKafkaTemplate(
      @Qualifier("defaultKafkaProperties") final KafkaProperties kafkaProperties) {
    return new KafkaTemplate<>(kafkaProducerFactory(kafkaProperties));
  }

  private ProducerFactory<String, String> kafkaProducerFactory(
      final KafkaProperties kafkaProperties) {
    return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
  }
}
