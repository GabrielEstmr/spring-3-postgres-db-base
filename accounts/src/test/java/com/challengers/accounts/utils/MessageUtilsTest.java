package com.challengers.accounts.utils;

import com.challengers.accounts.support.TestSupport;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

public class MessageUtilsTest extends TestSupport {

  @InjectMocks private MessageUtils provider;

  @Mock private MessageSource messageSource;

  @Test
  public void shouldGetFieldRequiredMessage() {
    final String keyLabel = "any.label";
    final String label = "label";

    when(messageSource.getMessage(keyLabel, new Object[] {}, getLocale())).thenReturn(label);
    provider.getFieldRequiredMessage(keyLabel);
    verify(messageSource).getMessage(keyLabel, new Object[] {}, getLocale());
    verify(messageSource).getMessage("required.field", new Object[] {label}, getLocale());
  }

  @Test
  public void shouldGetMessage() {
    final String key = "any.label";
    final String label = "label";

    when(messageSource.getMessage(key, new Object[] {}, getLocale())).thenReturn(label);
    final String result = provider.getMessage(key);
    verify(messageSource).getMessage(key, new Object[] {}, getLocale());
    assertEquals(label, result);
  }

  @Test
  public void shouldGetMessageDefaultLocale() {
    final String key = "any.label";
    final String label = "label";

    when(messageSource.getMessage(key, new Object[] {}, Locale.US)).thenReturn(label);
    final String result = provider.getMessageDefaultLocale(key);
    verify(messageSource).getMessage(key, new Object[] {}, Locale.US);
    assertEquals(label, result);
  }

  @Test
  public void shouldGetMessageWithParameters() {
    final String key = "any.label";
    final String param = "any.label";

    provider.getMessage(key, param);
    verify(messageSource).getMessage(key, new Object[] {param}, getLocale());
  }

  @Test
  public void shouldGetResourceNotFoundMessage() {
    final String keyLabel = "any.label";
    final String label = "label";

    when(messageSource.getMessage(keyLabel, new Object[] {}, getLocale())).thenReturn(label);
    provider.getResourceNotFoundMessage(keyLabel);
    verify(messageSource).getMessage(keyLabel, new Object[] {}, getLocale());
    verify(messageSource).getMessage("resource.not.found", new Object[] {label}, getLocale());
  }
}
