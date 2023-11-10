package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.domains.exceptions.ConflictException;
import com.challengers.accounts.gateways.output.AccountDatabaseGateway;
import com.challengers.accounts.support.TestSupport;
import com.challengers.accounts.templates.FixtureTemplate;
import com.challengers.accounts.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CreateAccountTest extends TestSupport {

  private static final String MSG_ERROR_ACCOUNT_SAME_DOCUMENT_NUMBER_FOUND =
      "there.is.an.account.for.given.document.id";
  private static final String MSG_ERROR_RESPONSE = "response";

  private static final String DOCUMENT_NUMBER = "12345";

  @InjectMocks private CreateAccount provider;
  @Mock private AccountDatabaseGateway accountDatabaseGateway;
  @Mock private MessageUtils messageUtils;

  @Test
  public void shouldThrowConflictExceptionWhenDocumentNumberAlreadyExists() {
    assertThrows(
        ConflictException.class,
        () -> {
          final var account = createObject(Account.class, FixtureTemplate.VALID);
          when(accountDatabaseGateway.findByDocumentNumber(DOCUMENT_NUMBER))
              .thenReturn(Optional.of(account));
          when(messageUtils.getMessageDefaultLocale(MSG_ERROR_ACCOUNT_SAME_DOCUMENT_NUMBER_FOUND))
              .thenReturn(MSG_ERROR_RESPONSE);
          try {
            provider.execute(DOCUMENT_NUMBER);
          } catch (final Exception e) {
            verify(accountDatabaseGateway, times(0)).save(any());
            throw e;
          }
        });
  }

  @Test
  public void shouldSaveAccountWhenDocumentIdIsANewOne() {
    final var account = createObject(Account.class, FixtureTemplate.VALID);
    when(accountDatabaseGateway.findByDocumentNumber(DOCUMENT_NUMBER)).thenReturn(Optional.empty());
    when(accountDatabaseGateway.save(any())).thenReturn(account);

    final var result = provider.execute(DOCUMENT_NUMBER);
    verifyNoInteractions(messageUtils);
    assertEquals(account, result);
  }
}
