package com.challengers.accounts.gateways.postgres.repositories;

import com.challengers.accounts.gateways.postgres.documents.TransactionDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class CustomTransactionRepositoryImpl implements CustomTransactionRepository {

  @PersistenceContext private final EntityManager entityManager;

  @Override
  public TransactionDocument lockById(final Long id, final LockModeType lockMode) {
    return entityManager.find(TransactionDocument.class, id, lockMode);
  }
}
