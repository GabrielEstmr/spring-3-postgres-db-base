package com.challengers.accounts.gateways.output.postgres.repositories;

import com.challengers.accounts.gateways.output.postgres.documents.TransactionDocument;

import jakarta.persistence.LockModeType;

public interface CustomTransactionRepository {

  TransactionDocument lockById(Long id, LockModeType lockMode);
}
