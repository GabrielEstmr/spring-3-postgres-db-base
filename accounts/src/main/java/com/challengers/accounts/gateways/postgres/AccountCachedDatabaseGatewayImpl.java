package com.challengers.accounts.gateways.postgres;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.gateways.AccountCacheDatabaseGateway;
import com.challengers.accounts.gateways.AccountDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Primary
@Qualifier("accountCachedDatabaseGatewayImpl")
@RequiredArgsConstructor
public class AccountCachedDatabaseGatewayImpl implements AccountDatabaseGateway {

  @Qualifier("accountDatabaseGatewayImpl")
  private final AccountDatabaseGateway accountDatabaseGateway;

  private final AccountCacheDatabaseGateway accountCacheDatabaseGateway;

  @Override
  public Account save(final Account account) {
    final var persistedAccount = accountDatabaseGateway.save(account);
    asyncSaveAccountIntoCaching(persistedAccount);
    return persistedAccount;
  }

  @Override
  public Optional<Account> findById(final Long id) {
    final var byId = accountCacheDatabaseGateway.findById(id);
    if (byId.isEmpty()) {
      final var persistedAccount = accountDatabaseGateway.findById(id);
      persistedAccount.ifPresent(this::asyncSaveAccountIntoCaching);
      return persistedAccount;
    }
    return byId;
  }

  @Override
  public Optional<Account> findByDocumentNumber(final String documentNumber) {
    final var byDocumentNumber = accountCacheDatabaseGateway.findByDocumentNumber(documentNumber);
    if (byDocumentNumber.isEmpty()) {
      final var persistedAccount = accountDatabaseGateway.findByDocumentNumber(documentNumber);
      persistedAccount.ifPresent(this::asyncSaveAccountIntoCaching);
      return persistedAccount;
    }
    return byDocumentNumber;
  }

  private void asyncSaveAccountIntoCaching(final Account persistedAccount) {
    final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    executorService.submit(() -> accountCacheDatabaseGateway.save(persistedAccount));
    executorService.close();
  }
}
