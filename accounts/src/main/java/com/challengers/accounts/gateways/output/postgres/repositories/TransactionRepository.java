package com.challengers.accounts.gateways.output.postgres.repositories;

import com.challengers.accounts.gateways.output.postgres.documents.TransactionDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository
    extends JpaRepository<TransactionDocument, Long>, CustomTransactionRepository {}
