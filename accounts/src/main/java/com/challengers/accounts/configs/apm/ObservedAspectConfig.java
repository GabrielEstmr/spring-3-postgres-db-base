package com.challengers.accounts.configs.apm;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test-container")
public class ObservedAspectConfig {

  @Bean
  public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
    return new ObservedAspect(observationRegistry);
  }
}
