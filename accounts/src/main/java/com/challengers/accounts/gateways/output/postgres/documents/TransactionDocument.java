package com.challengers.accounts.gateways.output.postgres.documents;

import com.challengers.accounts.domains.Transaction;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "tb_transaction",
    schema = "public",
    indexes = {@Index(name = "tb_transaction_idx_account_id", columnList = "account_id")})
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionDocument implements Serializable {
  @Serial private static final long serialVersionUID = -7893278923542070844L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_id")
  private Long accountId;

  @Column(name = "operation_type_d", nullable = false, length = 10)
  private Integer operationTypeId;

  @Column(nullable = false)
  private BigDecimal amount;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false)
  private LocalDateTime createdDate;

  @UpdateTimestamp
  @Column(name = "last_modified_date", nullable = false)
  private LocalDateTime lastModifiedDate;

  public TransactionDocument(final Transaction transaction) {
    this.id = transaction.getId();
    this.accountId = transaction.getAccountId();
    this.operationTypeId = transaction.getOperationTypeId();
    this.amount = transaction.getAmount();
    this.createdDate = transaction.getCreatedDate();
    this.lastModifiedDate = transaction.getLastModifiedDate();
  }

  public Transaction toDomain() {
    return Transaction.builder()
        .id(this.id)
        .accountId(this.accountId)
        .operationTypeId(this.operationTypeId)
        .amount(this.amount)
        .createdDate(this.createdDate)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}
