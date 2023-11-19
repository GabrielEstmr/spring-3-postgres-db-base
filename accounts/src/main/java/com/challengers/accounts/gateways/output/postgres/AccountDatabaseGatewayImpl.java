package com.challengers.accounts.gateways.output.postgres;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.gateways.output.AccountDatabaseGateway;
import com.challengers.accounts.gateways.output.postgres.documents.AccountDocument;
import com.challengers.accounts.gateways.output.postgres.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountDatabaseGatewayImpl implements AccountDatabaseGateway {

  private final AccountRepository accountRepository;

  @Caching(
      evict = {
        @CacheEvict(
            value = {
              "app-account-challenger:AccountDatabaseGatewayImpl:findById",
            },
            key = "'id:' + #account.id",
            cacheManager = "defaultCacheManager"),
        @CacheEvict(
            value = {
              "app-account-challenger:AccountDatabaseGatewayImpl:findByDocumentNumber",
            },
            key = "'documentNumber:' + #account.documentNumber",
            cacheManager = "defaultCacheManager")
      })
  @Override
  public Account save(final Account account) {
    return accountRepository.save(new AccountDocument(account)).toDomain();
  }

  @Cacheable(
      value = {
        "app-account-challenger:AccountDatabaseGatewayImpl:findById",
      },
      key = "'id:' + #id",
      cacheManager = "defaultCacheManager")
  @Override
  public Optional<Account> findById(final Long id) {
    return accountRepository.findById(id).map(AccountDocument::toDomain);
  }

  @Cacheable(
      value = {
        "app-account-challenger:AccountDatabaseGatewayImpl:findByDocumentNumber",
      },
      key = "'documentNumber:' + #documentNumber",
      cacheManager = "defaultCacheManager")
  @Override
  public Optional<Account> findByDocumentNumber(final String documentNumber) {
    return accountRepository.findByDocumentNumber(documentNumber).map(AccountDocument::toDomain);
  }
}
