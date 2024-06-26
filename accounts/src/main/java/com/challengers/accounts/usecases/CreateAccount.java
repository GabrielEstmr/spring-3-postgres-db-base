package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.gateways.output.AccountDatabaseGateway;
import com.challengers.accounts.gateways.output.DefaultTopicSenderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAccount {

  private final CheckDocumentNumberIsAlreadyRegistered checkDocumentNumberIsAlreadyRegistered;

  @Qualifier("accountCachedDatabaseGatewayImpl")
  private final AccountDatabaseGateway accountDatabaseGateway;

  private final DefaultTopicSenderGateway defaultTopicSenderGateway;

  public Account execute(final String documentNumber) {
    log.info("Creating Account for documentNumber: {}", documentNumber);
    checkDocumentNumberIsAlreadyRegistered.execute(documentNumber);
    final var account = accountDatabaseGateway.save(new Account(documentNumber));
    log.info("Account successfully created. accountId: {}", account.getId());
    defaultTopicSenderGateway.send(account.getId().toString());
    return account;
  }
}
