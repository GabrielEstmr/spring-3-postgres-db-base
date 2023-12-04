package com.challengers.accounts.configs.kafka;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@AllArgsConstructor
public class CommonConsumerConfiguration {

  private final ConsumerConfiguration consumer;
  private final ListenerConfiguration listener;

  public ConcurrentKafkaListenerContainerFactory<String, String> listenerContainerFactory() {
    return buildFactory(consumerConfigsDefault());
  }

  private ConcurrentKafkaListenerContainerFactory<String, String> buildFactory(
      Map<String, Object> configs) {
    final ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(
        new DefaultKafkaConsumerFactory<>(
            configs, new StringDeserializer(), new StringDeserializer()));
    factory.setConcurrency(listener.getConcurrency());
    factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 2L)));
    return factory;
  }

  private Map<String, Object> consumerConfigsDefault() {
    final HashMap<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumer.getBootstrapServers());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.getGroupId());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.getAutoOffsetReset());
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumer.getMaxPollRecords());
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumer.getSessionTimeoutMs());
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, consumer.getMaxPollIntervalMs());
    return props;
  }
}
