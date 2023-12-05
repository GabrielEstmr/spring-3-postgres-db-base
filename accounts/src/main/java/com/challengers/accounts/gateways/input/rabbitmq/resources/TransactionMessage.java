package com.challengers.accounts.gateways.input.rabbitmq.resources;

import com.challengers.accounts.domains.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class TransactionMessage implements Serializable {
  @Serial private static final long serialVersionUID = 5508476683250339694L;

  private Long id;

  public TransactionMessage(final Transaction transaction) {
    this.id = transaction.getId();
  }
}
