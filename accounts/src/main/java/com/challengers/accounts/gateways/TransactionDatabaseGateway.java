package com.challengers.accounts.gateways;

import com.challengers.accounts.domains.DatabaseLockMode;
import com.challengers.accounts.domains.Transaction;

import java.util.Optional;

public interface TransactionDatabaseGateway {
  Transaction save(Transaction transaction);
  Optional<Transaction> findById(Long id);
  Transaction lockById(Long id, DatabaseLockMode lockMode);
}
