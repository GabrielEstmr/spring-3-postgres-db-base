package com.challengers.accounts.gateways.redis;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.gateways.AccountCacheDatabaseGateway;
import com.challengers.accounts.gateways.redis.documents.AccountCacheDocument;
import com.challengers.accounts.gateways.redis.repositories.AccountCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountCacheDatabaseGatewayImpl implements AccountCacheDatabaseGateway {

  private final AccountCacheRepository accountCacheRepository;

  @Override
  public Account save(final Account account) {
    return accountCacheRepository.save(new AccountCacheDocument(account)).toDomain();
  }

  @Override
  public Optional<Account> findById(final Long id) {
    return accountCacheRepository.findById(id).map(AccountCacheDocument::toDomain);
  }

  @Override
  public Optional<Account> findByDocumentNumber(final String documentNumber) {
    return accountCacheRepository
        .findByDocumentNumber(documentNumber)
        .map(AccountCacheDocument::toDomain);
  }

  @Override
  public void deleteAll() {
    accountCacheRepository.deleteAll();
  }
}
