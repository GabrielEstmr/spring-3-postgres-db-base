package com.challengers.accounts.gateways.ff4j.resources;

import lombok.Getter;
import org.ff4j.property.Property;
import org.ff4j.property.PropertyString;

@Getter
public enum Properties {
  CONCILIATION_RECIPIENTID_EXCEPTION_LIST(
      "CONCILIATION_RECIPIENTID_EXCEPTION_LIST",
      "Conciliation RecipientId Exception List",
      PropertyString.class,
      new String(""));

  private String key;
  private String description;
  private Class<? extends Property> propertyType;
  private Object defaultValue;

  Properties(
      final String key,
      final String description,
      final Class<? extends Property> propertyType,
      final Object defaultValue) {
    this.key = key;
    this.description = description;
    this.propertyType = propertyType;
    this.defaultValue = defaultValue;
  }
}
