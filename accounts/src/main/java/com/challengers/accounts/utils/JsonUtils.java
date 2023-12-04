package com.challengers.accounts.utils;

import com.challengers.accounts.domains.exceptions.JsonUtilsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonUtils {

  private final ObjectMapper mapper;

  public String convertObjectToJSON(final Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new JsonUtilsException("Fail to serialize object: " + object, e);
    }
  }

  public <T> T toObject(final String json, final Class<T> valueType) {
    try {
      return mapper.readValue(json, valueType);
    } catch (JsonProcessingException e) {
      throw new JsonUtilsException(e);
    }
  }

  public <T> T toObject(
      final String json, final Class<?> parametrized, final Class<?> parameterClass) {
    try {
      JavaType valueType =
          mapper.getTypeFactory().constructParametricType(parametrized, parameterClass);

      return mapper.readValue(json, valueType);
    } catch (JsonProcessingException e) {
      throw new JsonUtilsException(e);
    }
  }
}
