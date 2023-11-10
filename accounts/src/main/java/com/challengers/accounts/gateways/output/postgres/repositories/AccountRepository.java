package com.challengers.accounts.gateways.output.postgres.repositories;

import com.challengers.accounts.gateways.output.postgres.documents.AccountDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountDocument, Long> {

    Optional<AccountDocument> findByDocumentNumber(String id);

}
