package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.domains.exceptions.ResourceNotFoundException;
import com.challengers.accounts.gateways.output.TransactionDatabaseGateway;
import com.challengers.accounts.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindTransaction {

  private static final String MSG_ERROR_TRANSACTION_NOT_FOUND =
      "there.is.no.transaction.for.given.id";

  private final TransactionDatabaseGateway transactionDatabaseGateway;
  private final MessageUtils messageUtils;

  public Transaction execute(final Long transactionId) {
    log.debug("Requesting Transaction with id: {}", transactionId);
    return transactionDatabaseGateway
        .findById(transactionId)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    messageUtils.getMessageDefaultLocale(MSG_ERROR_TRANSACTION_NOT_FOUND)));
  }
}
