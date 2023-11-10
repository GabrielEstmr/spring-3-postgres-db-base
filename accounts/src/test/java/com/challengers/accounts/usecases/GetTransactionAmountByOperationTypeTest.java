package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.OperationType;
import com.challengers.accounts.support.TestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GetTransactionAmountByOperationTypeTest extends TestSupport {

  private static final BigDecimal AMOUNT = BigDecimal.valueOf(12.45);
  private static final Long MINUS_ONE = -1L;

  @InjectMocks private GetTransactionAmountByOperationType provider;

  @Test
  public void shouldReturnPositiveValue() {
    Arrays.stream(OperationType.values())
        .filter(OperationType::getIsCredit)
        .forEach(op -> assertEquals(AMOUNT, provider.execute(op, AMOUNT)));
  }

  @Test
  public void shouldReturnNegativeValue() {
    Arrays.stream(OperationType.values())
        .filter(operationType -> !operationType.getIsCredit())
        .forEach(
            op ->
                assertEquals(
                    AMOUNT.multiply(BigDecimal.valueOf(MINUS_ONE)), provider.execute(op, AMOUNT)));
  }
}
