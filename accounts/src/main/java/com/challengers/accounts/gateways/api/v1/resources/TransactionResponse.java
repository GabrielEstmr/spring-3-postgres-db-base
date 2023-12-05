package com.challengers.accounts.gateways.api.v1.resources;

import com.challengers.accounts.domains.Transaction;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.util.Objects.nonNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionResponse extends RepresentationModel<TransactionResponse>
    implements Serializable {
  @Serial private static final long serialVersionUID = 4773823411137855836L;

  private Long id;
  private Long account_id;
  private Integer operation_type_id;
  private BigDecimal amount;

  public TransactionResponse(final Transaction transaction) {
    this.id = transaction.getId();
    this.account_id = transaction.getAccountId();
    this.operation_type_id = transaction.getOperationTypeId();
    this.amount = getRoundedValue(transaction.getAmount());
  }

  public static BigDecimal getRoundedValue(final BigDecimal value) {
    return nonNull(value) ? value.setScale(2, RoundingMode.HALF_EVEN) : null;
  }
}
