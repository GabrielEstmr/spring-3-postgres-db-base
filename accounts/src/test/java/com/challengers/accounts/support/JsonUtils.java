package com.challengers.accounts.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

  public static String convertObjectToJSON(final Object object) {
    final ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(final String json, final Class<T> type) {
    final ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(json, type);
    } catch (final IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
