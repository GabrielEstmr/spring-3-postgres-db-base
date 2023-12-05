package com.challengers.accounts.gateways.redis.repositories;

import com.challengers.accounts.gateways.redis.documents.AccountCacheDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountCacheRepository extends CrudRepository<AccountCacheDocument, Long> {
//  AccountCacheDocument save(AccountCacheDocument account);
//
//  Optional<AccountCacheDocument> findById(Long id);

  Optional<AccountCacheDocument> findByDocumentNumber(String documentNumber);
}
