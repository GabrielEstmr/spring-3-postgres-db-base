package com.challengers.accounts.configs.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@Configuration
@EnableRedisRepositories
public class CacheTemplate {

  //  List<String> clusterNodes = List.of("localhost:6379");

  @Bean
  @Primary
  JedisConnectionFactory connectionFactory() {
    final RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName("localhost");
    redisStandaloneConfiguration.setPort(6379);
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  @Primary
  public RedisTemplate<String, Object> defaultRedisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new JdkSerializationRedisSerializer());
    template.setValueSerializer(new JdkSerializationRedisSerializer());
    template.setEnableTransactionSupport(Boolean.TRUE);
    template.afterPropertiesSet();
    return template;
  }
}
