package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.OperationType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GetTransactionAmountByOperationType {

  private static final Long MINUS_ONE = -1L;

  public BigDecimal execute(final OperationType operationType, final BigDecimal amount) {
    return amount.multiply(getMultiplicationFactorByOperationType(operationType));
  }

  private BigDecimal getMultiplicationFactorByOperationType(final OperationType operationType) {
    return operationType.getIsCredit() ? BigDecimal.ONE : BigDecimal.valueOf(MINUS_ONE);
  }
}
