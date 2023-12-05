package com.challengers.accounts.configs.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProcessIntegrationRetryRabbitMQConfiguration {

  private static final String TTL_PROPERTY = "x-message-ttl";
  private static final String DLQ_EXCHANGE_PROPERTY = "x-dead-letter-exchange";
  private static final String DLQ_ROUTING_KEY_PROPERTY = "x-dead-letter-routing-key";

  @Value("${spring.rabbitmq.process-integration.time-to-live-milliseconds:600000}")
  private int internalOrderDelayedTTL;

  @Autowired
  @Qualifier("marketplaceAdminConfiguration")
  private RabbitAdmin marketplaceAdminConfiguration;

  @Bean
  public TopicExchange exchangeProcessIntegration() {
    final TopicExchange topicExchange =
        new TopicExchange(RabbitMQExchangeDlqTopicBound.PROCESS_INTEGRATION.getExchange());
    topicExchange.setAdminsThatShouldDeclare(marketplaceAdminConfiguration);
    return topicExchange;
  }

  @Bean
  public Queue processIntegrationMainQueue() {
    final Map<String, Object> queueArgs = new HashMap<>();
    queueArgs.put(TTL_PROPERTY, internalOrderDelayedTTL);
    queueArgs.put(
        DLQ_EXCHANGE_PROPERTY, RabbitMQExchangeDlqTopicBound.PROCESS_INTEGRATION.getExchange());
    queueArgs.put(
        DLQ_ROUTING_KEY_PROPERTY,
        RabbitMQExchangeDlqTopicBound.PROCESS_INTEGRATION.getDlqRoutingKey());

    final Queue queue = new Queue(RabbitMQExchangeDlqTopicBound.PROCESS_INTEGRATION.getMain());
    queue.getArguments().putAll(queueArgs);
    queue.setAdminsThatShouldDeclare(marketplaceAdminConfiguration);
    return queue;
  }

  @Bean
  public Binding bindingProcessIntegrationMainQueue(
      final Queue processIntegrationMainQueue, final TopicExchange exchangeProcessIntegration) {
    final Binding binding =
        BindingBuilder.bind(processIntegrationMainQueue)
            .to(exchangeProcessIntegration)
            .with(RabbitMQExchangeDlqTopicBound.PROCESS_INTEGRATION.getMainRoutingKey());
    binding.setAdminsThatShouldDeclare(marketplaceAdminConfiguration);
    return binding;
  }

  @Bean
  public Queue processIntegrationDlqQueue() {
    final Queue queue = new Queue(RabbitMQExchangeDlqTopicBound.PROCESS_INTEGRATION.getDlq());
    queue.setAdminsThatShouldDeclare(marketplaceAdminConfiguration);
    return queue;
  }

  @Bean
  public Binding bidingProcessIntegrationDlqQueue(
      final Queue processIntegrationDlqQueue, final TopicExchange exchangeProcessIntegration) {
    final Binding binding =
        BindingBuilder.bind(processIntegrationDlqQueue)
            .to(exchangeProcessIntegration)
            .with(RabbitMQExchangeDlqTopicBound.PROCESS_INTEGRATION.getDlqRoutingKey());
    binding.setAdminsThatShouldDeclare(marketplaceAdminConfiguration);
    return binding;
  }
}
