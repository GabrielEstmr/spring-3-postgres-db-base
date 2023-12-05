package com.challengers.accounts.configs.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RabbitMQExchangeDlqTopicBound {
  PROCESS_INTEGRATION(
      RabbitMQExchanges.APP_BASE_SPRING_PROCESS_INTEGRATION,
      RabbitMQQueues.APP_BASE_SPRING_PROCESS_INTEGRATION_QUEUE,
      RabbitMQRoutingKeys.APP_BASE_SPRING_PROCESS_INTEGRATION_QUEUE,
      RabbitMQQueues.APP_BASE_SPRING_PROCESS_INTEGRATION_DLQ_QUEUE,
      RabbitMQRoutingKeys.APP_BASE_SPRING_PROCESS_INTEGRATION_DLQ_QUEUE);

  private final String exchange;
  private final String main;
  private final String mainRoutingKey;
  private final String dlq;
  private final String dlqRoutingKey;
}
