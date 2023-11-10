package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.OperationType;
import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.gateways.output.TransactionDatabaseGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateTransaction {

  private final FindAccount findAccount;
  private final TransactionDatabaseGateway transactionDatabaseGateway;
  private final GetOperationTypeByIdOrThrowsStandardError getOperationTypeByIdOrThrowsStandardError;
  private final GetTransactionAmountByOperationType getTransactionAmountByOperationType;

  public Transaction execute(
      final Long accountId, final Integer operationTypeId, final BigDecimal amount) {
    log.info(
        "Creating Transaction for accountId: {} operationTypeId: {} amount: {}",
        accountId,
        operationTypeId,
        amount);
    final OperationType operationType =
        getOperationTypeByIdOrThrowsStandardError.execute(operationTypeId);
    final var account = findAccount.execute(accountId);
    final Transaction transaction =
        transactionDatabaseGateway.save(
            new Transaction(
                account.getId(),
                operationType,
                getTransactionAmountByOperationType.execute(operationType, amount)));
    log.info("Transaction successfully created. transactionId: {}", transaction.getId());
    return transaction;
  }
}
