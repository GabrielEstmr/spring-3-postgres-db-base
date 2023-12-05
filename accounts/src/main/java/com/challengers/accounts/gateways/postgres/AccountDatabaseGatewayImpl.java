package com.challengers.accounts.gateways.postgres;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.gateways.AccountDatabaseGateway;
import com.challengers.accounts.gateways.postgres.documents.AccountDocument;
import com.challengers.accounts.gateways.postgres.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Qualifier("accountDatabaseGatewayImpl")
@RequiredArgsConstructor
public class AccountDatabaseGatewayImpl implements AccountDatabaseGateway {

  private final AccountRepository accountRepository;

  @Override
  public Account save(final Account account) {
    return accountRepository.save(new AccountDocument(account)).toDomain();
  }

  @Override
  public Optional<Account> findById(final Long id) {
    return accountRepository.findById(id).map(AccountDocument::toDomain);
  }

  @Override
  public Optional<Account> findByDocumentNumber(final String documentNumber) {
    return accountRepository.findByDocumentNumber(documentNumber).map(AccountDocument::toDomain);
  }
}
