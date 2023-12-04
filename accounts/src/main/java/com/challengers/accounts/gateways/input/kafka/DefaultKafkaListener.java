package com.challengers.accounts.gateways.input.kafka;

import com.challengers.accounts.domains.Event;
import com.challengers.accounts.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.challengers.accounts.configs.kafka.Topics.APP_COMMON_DEFAULT_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultKafkaListener {

  private final JsonUtils jsonUtils;

  @KafkaListener(
      topics = APP_COMMON_DEFAULT_TOPIC,
      containerFactory = "kafkaDefaultListenerFactory")
  public void listen(
      @Payload final String message,
      @Header(KafkaHeaders.RECEIVED_PARTITION) String partitionId,
      @Header(KafkaHeaders.OFFSET) String offset,
      @Header(KafkaHeaders.RECEIVED_PARTITION) String key) {

    try {
      log.info(
          "Message received from topic: {} partitionId: {} offset: {} key: {} message: {}",
          APP_COMMON_DEFAULT_TOPIC,
          partitionId,
          offset,
          key,
          message);
      final Event<String> event = jsonUtils.toObject(message, Event.class, String.class);
      final var docCreationId = event.getPayload();

      log.info(docCreationId);

    } catch (final Exception e) {
      log.error(e.getLocalizedMessage(), e);
    }
  }
}
