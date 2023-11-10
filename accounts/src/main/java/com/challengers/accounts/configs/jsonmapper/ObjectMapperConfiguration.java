package com.challengers.accounts.configs.jsonmapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ObjectMapperConfiguration {

  @Bean
  public ObjectMapper getObjectMapper() {
    return Jackson2ObjectMapperBuilder.json()
        .failOnUnknownProperties(false)
        .serializationInclusion(JsonInclude.Include.NON_EMPTY)
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
        .modules(new JavaTimeModule())
        .build();
  }
}
