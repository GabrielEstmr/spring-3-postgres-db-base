package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.domains.exceptions.ConflictException;
import com.challengers.accounts.gateways.output.AccountCacheDatabaseGateway;
import com.challengers.accounts.gateways.output.AccountDatabaseGateway;
import com.challengers.accounts.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAccount {

  private static final String MSG_ERROR_ACCOUNT_SAME_DOCUMENT_NUMBER_FOUND =
      "there.is.an.account.for.given.document.id";

  private final AccountDatabaseGateway accountDatabaseGateway;
  private final AccountCacheDatabaseGateway accountCacheDatabaseGateway;
  private final MessageUtils messageUtils;

  public Account execute(final String documentNumber) {
    log.info("Creating Account for documentNumber: {}", documentNumber);
    checkDocumentNumberIsAlreadyRegistered(documentNumber);
    final var account = accountDatabaseGateway.save(new Account(documentNumber));
    accountCacheDatabaseGateway.save(account);
    log.info("Account successfully created. accountId: {}", account.getId());
    return account;
  }

  private void checkDocumentNumberIsAlreadyRegistered(final String documentNumber) {
    accountDatabaseGateway
        .findByDocumentNumber(documentNumber)
        .ifPresent(
            account -> {
              throw new ConflictException(
                  messageUtils.getMessageDefaultLocale(
                      MSG_ERROR_ACCOUNT_SAME_DOCUMENT_NUMBER_FOUND));
            });
  }
}
