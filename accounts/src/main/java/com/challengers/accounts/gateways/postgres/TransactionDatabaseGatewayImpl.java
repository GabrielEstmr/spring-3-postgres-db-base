package com.challengers.accounts.gateways.postgres;

import com.challengers.accounts.domains.DatabaseLockMode;
import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.gateways.TransactionDatabaseGateway;
import com.challengers.accounts.gateways.postgres.documents.TransactionDocument;
import com.challengers.accounts.gateways.postgres.repositories.TransactionRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransactionDatabaseGatewayImpl implements TransactionDatabaseGateway {

  private final TransactionRepository transactionRepository;

  @Override
  @CachePut(
      value = {
        "app-account-challenger:TransactionDatabaseGatewayImpl:findById",
      },
      key = "'id:' + #transaction.id",
      cacheManager = "defaultCacheManager")
  public Transaction save(Transaction transaction) {
    return transactionRepository.save(new TransactionDocument(transaction)).toDomain();
  }

  @Override
  @Cacheable(
      value = {
        "app-account-challenger:TransactionDatabaseGatewayImpl:findById",
      },
      key = "'id:' + #id",
      cacheManager = "defaultCacheManager")
  public Optional<Transaction> findById(Long id) {
    return transactionRepository.findById(id).map(TransactionDocument::toDomain);
  }

  @Override
  public Transaction lockById(final Long id, final DatabaseLockMode lockMode) {
    return transactionRepository.lockById(id, LockModeType.valueOf(lockMode.name())).toDomain();
  }
}
