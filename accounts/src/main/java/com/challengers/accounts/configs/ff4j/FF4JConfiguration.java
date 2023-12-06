package com.challengers.accounts.configs.ff4j;

import com.challengers.accounts.gateways.ff4j.resources.Features;
import com.challengers.accounts.gateways.ff4j.resources.Properties;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.ff4j.FF4j;
import org.ff4j.cache.FF4JCacheManager;
import org.ff4j.cache.FF4jCacheManagerRedisLettuce;
import org.ff4j.core.Feature;
import org.ff4j.core.FeatureStore;
import org.ff4j.mongo.store.FeatureStoreMongo;
import org.ff4j.mongo.store.PropertyStoreMongo;
import org.ff4j.property.PropertyString;
import org.ff4j.property.store.PropertyStore;
import org.ff4j.web.ApiConfig;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = "org.ff4j.aop")
@ConditionalOnClass({FF4jDispatcherServlet.class})
public class FF4JConfiguration extends SpringBootServletInitializer {

  private static final String DEFAULT_CONSOLE = "/ff4j-console/*";
  private static final String COLLECTION_NAME = "ff4j-features";
  private static final String PROPERTY_COLLECTION = "ff4j-properties";

  @Value("${spring.data.redis.host:localhost}")
  private String redisHost;

  @Value("${spring.data.redis.port:6379}")
  private Integer redisPort;

  @Value("${spring.data.redis.ff4j.ttl-in-minutes:60}")
  private Integer redisTtlInMinutes;

  @Bean
  public FF4JCacheManager ff4jCacheManager() {
    final RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", 6379));
    final FF4jCacheManagerRedisLettuce cacheManager = new FF4jCacheManagerRedisLettuce(redisClient);
    cacheManager.setTimeToLive((int) Duration.ofMinutes(redisTtlInMinutes).getSeconds());
    return cacheManager;
  }

  @Bean
  public FF4j getFF4j(
      final FeatureStore featureStore,
      final PropertyStore propertyStore,
      final FF4JCacheManager cacheManager) {
    final FF4j ff4j = new FF4j();
    ff4j.setFeatureStore(featureStore);
    ff4j.setPropertiesStore(propertyStore);
    ff4j.cache(cacheManager);

    Arrays.stream(Features.values())
        .filter(feature -> !ff4j.getFeatureStore().exist(feature.getKey()))
        .forEach(feature -> createFeature(ff4j, feature));

    final Properties[] properties = Properties.values();
    Arrays.stream(properties)
        .filter(property -> !ff4j.getPropertiesStore().existProperty(property.getKey()))
        .forEach(property -> createProperty(ff4j, property));

    ff4j.disableAlterBeanThrowInvocationTargetException();
    return ff4j;
  }

  @Bean
  public ApiConfig getApiConfig(final FF4j ff4j) {
    final ApiConfig apiConfig = new ApiConfig();
    apiConfig.setAuthenticate(false);
    apiConfig.setAutorize(false);
    apiConfig.setFF4j(ff4j);
    return apiConfig;
  }

  @Bean
  public FeatureStore featureStore(
      final MongoClient mongoClient, final MongoTemplate mongoTemplate) {
    final MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoTemplate.getDb().getName());
    return new FeatureStoreMongo(mongoDatabase, COLLECTION_NAME);
  }

  @Bean
  public PropertyStore propertyStore(
      final MongoClient mongoClient, final MongoTemplate mongoTemplate) {
    final MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoTemplate.getDb().getName());
    return new PropertyStoreMongo(mongoDatabase, PROPERTY_COLLECTION);
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBeanToFF4j(final FF4j ff4j) {
    return new ServletRegistrationBean(getFF4jDispatcherServlet(ff4j), DEFAULT_CONSOLE);
  }

  @Bean
  @ConditionalOnMissingBean
  public FF4jDispatcherServlet getFF4jDispatcherServlet(FF4j ff4j) {
    FF4jDispatcherServlet ff4jConsoleServlet = new FF4jDispatcherServlet();
    ff4jConsoleServlet.setFf4j(ff4j);
    return ff4jConsoleServlet;
  }

  private void createFeature(final FF4j ff4j, final Features feature) {
    final Feature fp = new Feature(feature.getKey(), feature.isDefaultValue());
    fp.setDescription(feature.getDescription());
    fp.setGroup(feature.getGroup());
    ff4j.createFeature(fp);
  }

  private void createProperty(final FF4j ff4j, final Properties property) {
    final String propertyKey = property.getKey();

    final PropertyString propertyString =
        new PropertyString(propertyKey, String.valueOf(property.getDefaultValue()));
    propertyString.setDescription(property.getDescription());
    ff4j.createProperty(propertyString);
  }
}
