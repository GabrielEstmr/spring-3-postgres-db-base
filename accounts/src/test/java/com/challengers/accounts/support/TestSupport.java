package com.challengers.accounts.support;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.challengers.accounts.templates.FixtureTemplate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class TestSupport {

  private static ObjectMapper objectMapper;

  @BeforeAll
  public static void setup() {
    FixtureFactoryLoader.loadTemplates("com.challengers.accounts.templates");
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public <T> T createObject(final Class<T> clazz, final FixtureTemplate template) {
    return Fixture.from(clazz).gimme(template.name());
  }

  public <T> List<T> createObjects(
      final Class<T> clazz, int quantity, final FixtureTemplate template) {
    return Fixture.from(clazz).gimme(quantity, template.name());
  }

  public <T> List<T> createObjects(
      final Class<T> clazz, int quantity, final FixtureTemplate... templates) {
    final List<String> templateNames =
        Arrays.stream(templates).map(Enum::name).collect(Collectors.toList());

    return Fixture.from(clazz).gimme(quantity, templateNames);
  }
}
