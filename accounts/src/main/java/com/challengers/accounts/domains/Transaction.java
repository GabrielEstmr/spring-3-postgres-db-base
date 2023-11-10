package com.challengers.accounts.domains;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Transaction implements Serializable {
  @Serial private static final long serialVersionUID = 8240365626930392340L;

  private Long id;
  private Long accountId;
  private Integer operationTypeId;
  private BigDecimal amount;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public Transaction(
      final Long accountId, final OperationType operationType, final BigDecimal amount) {
    this.accountId = accountId;
    this.operationTypeId = operationType.getId();
    this.amount = amount;
  }
}
