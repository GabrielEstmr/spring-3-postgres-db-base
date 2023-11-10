package com.challengers.accounts.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@Component
@RequiredArgsConstructor
public class MessageUtils {

  private static final String REQUIRED_FIELD = "required.field";
  private static final String RESOURCE_NOT_FOUND = "resource.not.found";

  private final MessageSource messageSource;

  public String getFieldRequiredMessage(final String keyLabel) {
    final String sellerLabel = messageSource.getMessage(keyLabel, new Object[] {}, getLocale());
    return messageSource.getMessage(REQUIRED_FIELD, new Object[] {sellerLabel}, getLocale());
  }

  public String getResourceNotFoundMessage(final String keyLabel, final Object... params) {
    final String label = messageSource.getMessage(keyLabel, params, getLocale());
    return messageSource.getMessage(RESOURCE_NOT_FOUND, new Object[] {label}, getLocale());
  }

  public String getMessage(final String key, final Object... param) {
    return messageSource.getMessage(key, param, getLocale());
  }

  public String getMessageDefaultLocale(final String key, final Object... param) {
    return messageSource.getMessage(key, param, Locale.US);
  }
}
