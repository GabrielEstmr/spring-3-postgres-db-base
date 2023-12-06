package com.challengers.accounts.gateways;

import com.challengers.accounts.domains.Account;

import java.util.Optional;

public interface AccountCacheDatabaseGateway {
    Account save(Account account);

    Optional<Account> findById(Long id);

    Optional<Account> findByDocumentNumber(String documentNumber);

    void deleteAll();
}
