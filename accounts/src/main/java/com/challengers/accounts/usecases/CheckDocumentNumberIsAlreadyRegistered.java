package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.exceptions.ConflictException;
import com.challengers.accounts.gateways.AccountDatabaseGateway;
import com.challengers.accounts.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckDocumentNumberIsAlreadyRegistered {

  private static final String MSG_ERROR_ACCOUNT_SAME_DOCUMENT_NUMBER_FOUND =
      "there.is.an.account.for.given.document.id";

  @Qualifier("accountCachedDatabaseGatewayImpl")
  private final AccountDatabaseGateway accountDatabaseGateway;
  private final MessageUtils messageUtils;

  public void execute(final String documentNumber) {
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
