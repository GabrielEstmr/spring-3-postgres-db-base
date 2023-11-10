package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.domains.OperationType;
import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.gateways.output.TransactionDatabaseGateway;
import com.challengers.accounts.support.TestSupport;
import com.challengers.accounts.templates.FixtureTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateTransactionTest extends TestSupport {

  private static final Long ACCOUNT_ID = 12345L;
  private static final BigDecimal AMOUNT = BigDecimal.TEN;
  private static final BigDecimal AMOUNT_BY_OPERATION = BigDecimal.valueOf(-10);
  private static final Integer OPERATIONAL_TYPE_ID = 1;
  private static final OperationType OPERATIONAL_TYPE = OperationType.PAYMENT;

  @InjectMocks private CreateTransaction provider;
  @Mock private FindAccount findAccount;
  @Mock private TransactionDatabaseGateway transactionDatabaseGateway;
  @Mock private GetOperationTypeByIdOrThrowsStandardError getOperationTypeByIdOrThrowsStandardError;
  @Mock private GetTransactionAmountByOperationType getTransactionAmountByOperationType;

  @Test
  public void shouldCreateTransaction() {
    final var account = createObject(Account.class, FixtureTemplate.VALID);
    final var transaction = createObject(Transaction.class, FixtureTemplate.VALID);

    final InOrder inOrder =
        inOrder(
            this.getOperationTypeByIdOrThrowsStandardError,
            this.findAccount,
            this.getTransactionAmountByOperationType,
            this.transactionDatabaseGateway);

    when(findAccount.execute(ACCOUNT_ID)).thenReturn(account);
    when(getOperationTypeByIdOrThrowsStandardError.execute(OPERATIONAL_TYPE_ID))
        .thenReturn(OPERATIONAL_TYPE);
    when(getTransactionAmountByOperationType.execute(OPERATIONAL_TYPE, AMOUNT))
        .thenReturn(AMOUNT_BY_OPERATION);
    when(transactionDatabaseGateway.save(
            new Transaction(account.getId(), OPERATIONAL_TYPE, AMOUNT_BY_OPERATION)))
        .thenReturn(transaction);

    final var result = provider.execute(ACCOUNT_ID, 1, AMOUNT);
    assertEquals(transaction, result);
    inOrder.verify(getOperationTypeByIdOrThrowsStandardError).execute(OPERATIONAL_TYPE_ID);
    inOrder.verify(findAccount).execute(ACCOUNT_ID);
    inOrder.verify(getTransactionAmountByOperationType).execute(OPERATIONAL_TYPE, AMOUNT);
    inOrder.verify(transactionDatabaseGateway).save(any());
  }
}
