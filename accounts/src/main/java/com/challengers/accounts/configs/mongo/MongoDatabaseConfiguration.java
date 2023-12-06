package com.challengers.accounts.configs.mongo;

import com.mongodb.ReadPreference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
    basePackages = {"com.netshoes.mpcommissionsplit.gateways.mongo.repositories"})
@EnableMongoAuditing
public class MongoDatabaseConfiguration {

  @Bean
  @Primary
  public MongoTemplate mongoTemplate(
      final MongoDatabaseFactory mongoDatabaseFactory, final MongoConverter mongoConverter) {
    return new MongoTemplate(mongoDatabaseFactory, mongoConverter);
  }

  @Bean
  public MongoTemplate secondaryMongoTemplate(
      final MongoDatabaseFactory mongoDatabaseFactory, final MongoConverter mongoConverter) {
    final MongoTemplate mongoTemplate = new MongoTemplate(mongoDatabaseFactory, mongoConverter);
    mongoTemplate.setReadPreference(ReadPreference.secondary());
    return mongoTemplate;
  }
}
