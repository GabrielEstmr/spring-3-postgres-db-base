package com.challengers.accounts.gateways.ff4j.resources;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Features {
  MP_ORDER_AUTOMATIC_REFUND_BROADCAST_INTEGRATION_FAILURE_FORCE_COMMIT(
      "enable-mp-order-automatic-refund-broadcast-failure-force-commit",
      "kafka-listener-retry",
      "Enable commit for messages that have failure in its automatic refund processing",
      false);

  private final String key;
  private final String group;
  private final String description;
  private final boolean defaultValue;
}
