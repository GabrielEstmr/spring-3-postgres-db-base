package com.challengers.accounts.gateways;

import com.challengers.accounts.gateways.ff4j.resources.Features;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface FeatureToggleGateway {

  boolean isFeatureEnabled(Features feature);

  boolean isFeatureDisabled(Features feature);

  List<String> getPropertyValuesList(String propertyKey);

  String getPropertyValueAsString(String propertyKey);

  BigDecimal getPropertyValueAsBigDecimal(String propertyKey);

  LocalDateTime getPropertyAsLocalDateTime(String propertyKey);

  void clearCache();
}
