package com.challengers.accounts.gateways.rabbitmq.producers;

import com.challengers.accounts.configs.rabbitmq.RabbitMQExchanges;
import com.challengers.accounts.configs.rabbitmq.RabbitMQRoutingKeys;
import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.gateways.AmqpEventSender;
import com.challengers.accounts.gateways.rabbitmq.resources.TransactionMessage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class AmqpEventSenderImpl implements AmqpEventSender {

  public static final String MSG_SENT_SUCCESSFULLY =
      "Message sent successfully: Exchange: {}, RoutingKey: {}, Message: {}";

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void send(@NotNull final Transaction transaction) {
    final var transactionMessage = new TransactionMessage(transaction);
    rabbitTemplate.convertAndSend(
        RabbitMQExchanges.APP_BASE_SPRING_PROCESS_INTEGRATION,
        RabbitMQRoutingKeys.APP_BASE_SPRING_PROCESS_INTEGRATION_QUEUE,
        transactionMessage);
    log.info(
        MSG_SENT_SUCCESSFULLY,
        RabbitMQExchanges.APP_BASE_SPRING_PROCESS_INTEGRATION,
        RabbitMQRoutingKeys.APP_BASE_SPRING_PROCESS_INTEGRATION_QUEUE,
        transactionMessage);
  }
}
