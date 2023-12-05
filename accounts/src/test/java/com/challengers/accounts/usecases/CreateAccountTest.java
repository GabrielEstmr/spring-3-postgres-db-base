package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.gateways.AccountDatabaseGateway;
import com.challengers.accounts.support.TestSupport;
import com.challengers.accounts.templates.FixtureTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreateAccountTest extends TestSupport {

  private static final String DOCUMENT_NUMBER = "12345";

  @InjectMocks private CreateAccount provider;

  @Mock private CheckDocumentNumberIsAlreadyRegistered checkDocumentNumberIsAlreadyRegistered;
  @Mock private AccountDatabaseGateway accountDatabaseGateway;

  @Test
  public void shouldCreateAccount() {
    final var account = createObject(Account.class, FixtureTemplate.VALID);
    account.setId(2L);
    when(accountDatabaseGateway.save(any())).thenReturn(account);

    final var result = provider.execute(DOCUMENT_NUMBER);
    verify(checkDocumentNumberIsAlreadyRegistered).execute(DOCUMENT_NUMBER);
    assertEquals(account, result);
  }
}
