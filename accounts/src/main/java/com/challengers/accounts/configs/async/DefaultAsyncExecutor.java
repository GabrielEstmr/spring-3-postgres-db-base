package com.challengers.accounts.configs.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class DefaultAsyncExecutor {

  @Value("${app-account-challenger.async.default.thread-max-core-pool-size}")
  private Integer threadConfigMaxCorePoolSize;

  @Value("${app-account-challenger.async.default.max-pool-size}")
  private Integer maxPoolSize;

  @Value("${app-account-challenger.async.default.queue-capacity}")
  private Integer queueCapacity;

  @Bean
  public Executor getDefaultExecutor() {
    final var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(threadConfigMaxCorePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setThreadNamePrefix("DefaultAsyncExecutor - ");
    executor.initialize();
    return executor;
  }
}
