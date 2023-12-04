package com.challengers.accounts.configs.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Getter
@Setter
@EnableKafka
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.default-config")
public class AbacosConfiguration {
  private ProducerConfiguration producer = new ProducerConfiguration();
  private ConsumerConfiguration consumer = new ConsumerConfiguration();
  private ListenerConfiguration listener = new ListenerConfiguration();

  @Bean("kafkaDefaultProducerFactory")
  public KafkaTemplate<String, String> kafkaDefaultProducerFactory() {
    final CommonProducerConfiguration commonProducerConfiguration =
        buildCommonProducerConfiguration();
    return commonProducerConfiguration.kafkaTemplateFactory();
  }

  @Bean("kafkaDefaultListenerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaDefaultListenerFactory() {

    final CommonConsumerConfiguration commonConsumerConfiguration =
        buildCommonConsumerConfiguration();

    return commonConsumerConfiguration.listenerContainerFactory();
  }

  private CommonConsumerConfiguration buildCommonConsumerConfiguration() {
    return new CommonConsumerConfiguration(consumer, listener);
  }

  private CommonProducerConfiguration buildCommonProducerConfiguration() {
    return new CommonProducerConfiguration(producer);
  }
}
