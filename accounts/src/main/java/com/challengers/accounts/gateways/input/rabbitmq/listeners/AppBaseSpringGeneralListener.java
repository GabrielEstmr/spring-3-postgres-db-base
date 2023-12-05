package com.challengers.accounts.gateways.input.rabbitmq.listeners;

import com.challengers.accounts.configs.rabbitmq.RabbitMQQueues;
import com.challengers.accounts.gateways.input.rabbitmq.resources.TransactionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppBaseSpringGeneralListener {

  private static final String MESSAGE_RECEIVED_FROM_QUEUE_MESSAGE =
      "Message received from queue: {}, message: {}";
  private final ObjectMapper objectMapper;

  @RabbitListener(
      queues = RabbitMQQueues.APP_BASE_SPRING_PROCESS_INTEGRATION_DLQ_QUEUE,
      containerFactory = "defaultMarketplaceListenerFactory")
  public void listen(final Message message) throws IOException {

    log.info(
        MESSAGE_RECEIVED_FROM_QUEUE_MESSAGE,
        RabbitMQQueues.APP_BASE_SPRING_PROCESS_INTEGRATION_DLQ_QUEUE,
        message);

    try {
      final TransactionMessage messageEvent =
          objectMapper.readValue(message.getBody(), TransactionMessage.class);

      log.info("MessageId {}", messageEvent.getId());

    } catch (final Exception e) {
      log.error(e.getLocalizedMessage(), e);
    }
  }
}
