package com.challengers.accounts.configs.kafka;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@AllArgsConstructor
public class CommonProducerConfiguration {

  private final ProducerConfiguration producerConfiguration;

  public KafkaTemplate<String, String> kafkaTemplateFactory() {
    final ProducerFactory<String, String> producerFactory =
        new DefaultKafkaProducerFactory<>(producerConfigsDefault());

    return new KafkaTemplate<>(producerFactory);
  }

  private Map<String, Object> producerConfigsDefault() {
    final Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerConfiguration.getBootstrapServers());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerConfiguration.getKeySerializer());
    props.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerConfiguration.getValueSerializer());
    props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
    return props;
  }
}
