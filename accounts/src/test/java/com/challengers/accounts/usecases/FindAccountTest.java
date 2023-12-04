package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.domains.exceptions.ResourceNotFoundException;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class FindAccountTest extends TestSupport {

  private static final Long ACCOUNT_ID = 12345L;
  private static final String MSG_ERROR_ACCOUNT_NOT_FOUND = "there.is.no.account.for.given.id";
  private static final String MSG_LOCALE = "msg";

  @InjectMocks private FindAccount provider;
  @Mock private AccountDatabaseGateway accountDatabaseGateway;
  @Mock private MessageUtils messageUtils;

  @Test
  public void shouldThrowResourceNotFoundExceptionWhenThereIsNoAccount() {

    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          when(accountDatabaseGateway.findById(ACCOUNT_ID)).thenReturn(Optional.empty());
          when(messageUtils.getMessageDefaultLocale(MSG_ERROR_ACCOUNT_NOT_FOUND))
              .thenReturn(MSG_LOCALE);
          provider.execute(ACCOUNT_ID);
        });
  }

  @Test
  public void shouldFindAndReturnAccount() {
    final var account = createObject(Account.class, FixtureTemplate.VALID);
    when(accountDatabaseGateway.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

    final var result = provider.execute(ACCOUNT_ID);
    assertEquals(account, result);
    verifyNoInteractions(messageUtils);
  }
}
