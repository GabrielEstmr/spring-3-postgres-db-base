package com.challengers.accounts.gateways.ff4j;

import com.challengers.accounts.gateways.FeatureToggleGateway;
import com.challengers.accounts.gateways.ff4j.resources.Features;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ff4j.FF4j;
import org.ff4j.cache.FF4jCacheProxy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
@RequiredArgsConstructor
public class FeatureToggleFF4JImpl implements FeatureToggleGateway {

  private static final String DEFAULT_SEPARATOR = ",";

  private final FF4j ff4j;

  @Override
  public boolean isFeatureEnabled(final Features feature) {
    return ff4j.check(feature.getKey());
  }

  @Override
  public boolean isFeatureDisabled(final Features feature) {
    return !isFeatureEnabled(feature);
  }

  @Override
  public List<String> getPropertyValuesList(final String propertyKey) {
    String sellerIds = ofNullable(getPropertyValueAsString(propertyKey)).orElse(EMPTY);

    return Stream.of(sellerIds.split(DEFAULT_SEPARATOR))
        .map(String::trim)
        .filter(StringUtils::isNotBlank)
        .collect(toList());
  }

  @Override
  public String getPropertyValueAsString(final String propertyKey) {
    return ff4j.getProperty(propertyKey).asString();
  }

  @Override
  public BigDecimal getPropertyValueAsBigDecimal(final String propertyKey) {
    return BigDecimal.valueOf(ff4j.getProperty(propertyKey).asDouble());
  }

  @Override
  public LocalDateTime getPropertyAsLocalDateTime(final String propertyKey) {
    return ofNullable(ff4j.getProperty(propertyKey).asString())
        .filter(StringUtils::isNotBlank)
        .map(LocalDateTime::parse)
        .orElse(null);
  }

  @Override
  public void clearCache() {
    final FF4jCacheProxy cacheProxy = ff4j.getCacheProxy();
    cacheProxy.getCacheManager().clearProperties();
    cacheProxy.getCacheManager().clearFeatures();
  }
}
