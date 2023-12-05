package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.domains.exceptions.ResourceNotFoundException;
import com.challengers.accounts.gateways.TransactionDatabaseGateway;
import com.challengers.accounts.support.TestSupport;
import com.challengers.accounts.templates.FixtureTemplate;
import com.challengers.accounts.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class FindTransactionTest extends TestSupport {

  private static final Long TRANSACTION_ID = 12345L;
  private static final String MSG_ERROR_TRANSACTION_NOT_FOUND =
      "there.is.no.transaction.for.given.id";
  private static final String MSG_LOCALE = "msg";

  @InjectMocks private FindTransaction provider;
  @Mock private TransactionDatabaseGateway transactionDatabaseGateway;
  @Mock private MessageUtils messageUtils;

  @Test
  public void shouldThrowResourceNotFoundExceptionWhenThereIsNoTransaction() {
    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          when(transactionDatabaseGateway.findById(TRANSACTION_ID)).thenReturn(Optional.empty());
          when(messageUtils.getMessageDefaultLocale(MSG_ERROR_TRANSACTION_NOT_FOUND))
              .thenReturn(MSG_LOCALE);
          provider.execute(TRANSACTION_ID);
        });
  }

  @Test
  public void shouldFindAndReturnAccount() {
    final var transaction = createObject(Transaction.class, FixtureTemplate.VALID);
    when(transactionDatabaseGateway.findById(TRANSACTION_ID)).thenReturn(Optional.of(transaction));
    final var result = provider.execute(TRANSACTION_ID);
    assertEquals(transaction, result);
    verifyNoInteractions(messageUtils);
  }
}
