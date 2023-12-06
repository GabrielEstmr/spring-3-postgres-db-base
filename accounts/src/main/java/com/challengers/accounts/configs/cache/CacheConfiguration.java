package com.challengers.accounts.configs.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

@Slf4j
@Configuration
@EnableCaching
public class CacheConfiguration {

  @Value("${spring.redis.default-manager.ttl-in-minutes:60}")
  private Integer defaultTtlInMinutes;

  @Bean
  @Primary
  public CacheManager defaultCacheManager(final RedisConnectionFactory redisConnectionFactory) {
    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(defaultCacheConfig().entryTtl(Duration.ofMinutes(defaultTtlInMinutes)))
        .build();
  }
}
