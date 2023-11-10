package com.challengers.accounts.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OperationType {
  PURCHASE_IN_SIGHT(1, "COMPRA A VISTA", false),
  INSTALLMENT_PURCHASE(2, "COMPRA PARCELADA", false),
  WITHDRAW(3, "SAQUE", false),
  PAYMENT(4, "PAGAMENTO", true);

  private static final String MSG_INVALID_OPERATION_TYPE_ID = "Invalid operation type id";

  private final Integer id;
  private final String description;
  private final Boolean isCredit;

  public static OperationType getById(final Integer id) throws RuntimeException {
    return Arrays.stream(OperationType.values())
        .filter(type -> type.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(MSG_INVALID_OPERATION_TYPE_ID));
  }
}
