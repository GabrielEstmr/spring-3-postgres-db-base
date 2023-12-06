package com.challengers.accounts.configs.cache;

import io.lettuce.core.ReadFrom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.support.locks.ExpirableLockRegistry;

import java.time.Duration;

@Slf4j
@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class CacheTemplate {

  private static final String REDIS_LOCK_REGISTRY_KEY = "REDIS_LOCK_REGISTRY_KEY";

  @Value("${spring.redis.locking.ttl-in-minutes:15}")
  private Integer lockingTtlInMinutes;

  private final RedisProperties redisProperties;

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName(redisProperties.getHost());
    config.setPort(redisProperties.getPort());
    final LettuceClientConfiguration clientConfig =
        LettuceClientConfiguration.builder().readFrom(ReadFrom.MASTER).build();
    return new LettuceConnectionFactory(config, clientConfig);
  }

  @Bean
  @Primary
  public RedisTemplate<String, Object> defaultRedisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new JdkSerializationRedisSerializer());
    template.setValueSerializer(new JdkSerializationRedisSerializer());
    template.setEnableTransactionSupport(Boolean.TRUE);
    template.afterPropertiesSet();
    return template;
  }

  @Bean("lockRegistry")
  public ExpirableLockRegistry lockRegistry() {
    return new RedisLockRegistry(
        redisConnectionFactory(),
        REDIS_LOCK_REGISTRY_KEY,
        Duration.ofMinutes(lockingTtlInMinutes).toMillis());
  }
}
