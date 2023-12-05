package com.challengers.accounts.configs.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitMQMarketplaceConfiguration {

  @Value("${spring.rabbitmq.listener.concurrency}")
  private Integer concurrentConsumers;

  @Value("${spring.rabbitmq.listener.max-concurrency}")
  private Integer maxConcurrentConsumers;

  @Autowired private RabbitProperties rabbitProperties;

  @Bean
  @Primary
  public ConnectionFactory rabbitMarketplaceConnectionFactory() {
    CachingConnectionFactory factory = new CachingConnectionFactory();
    factory.setUsername(rabbitProperties.getUsername());
    factory.setPassword(rabbitProperties.getPassword());
    factory.setAddresses(rabbitProperties.getAddresses());
    factory.setVirtualHost(rabbitProperties.getVirtualHost());
    return factory;
  }

  @Bean
  @Primary
  @Qualifier("marketplaceAdminConfiguration")
  public RabbitAdmin marketplaceAdminConfiguration() {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitMarketplaceConnectionFactory());
    rabbitAdmin.afterPropertiesSet();
    return rabbitAdmin;
  }

  @Bean
  @Primary
  public RabbitTemplate rabbitMarketplaceTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitMarketplaceConnectionFactory());
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    return rabbitTemplate;
  }

  @Primary
  @Bean(name = "defaultMarketplaceListenerFactory")
  public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
      final RabbitTemplate rabbitMarketplaceTemplate) {
    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(rabbitMarketplaceConnectionFactory());
    factory.setConcurrentConsumers(concurrentConsumers);
    factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
    return factory;
  }
}
