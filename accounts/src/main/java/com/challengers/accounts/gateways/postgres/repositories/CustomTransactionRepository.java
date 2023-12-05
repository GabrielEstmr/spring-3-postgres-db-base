package com.challengers.accounts.gateways.postgres.repositories;

import com.challengers.accounts.gateways.postgres.documents.TransactionDocument;

import jakarta.persistence.LockModeType;

public interface CustomTransactionRepository {

  TransactionDocument lockById(Long id, LockModeType lockMode);
}
