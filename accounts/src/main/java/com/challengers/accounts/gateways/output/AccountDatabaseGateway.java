package com.challengers.accounts.gateways.output;

import com.challengers.accounts.domains.Account;

import java.util.Optional;

public interface AccountDatabaseGateway {
  Account save(Account account);

  Optional<Account> findById(Long id);

  Optional<Account> findByDocumentNumber(String documentNumber);
}
