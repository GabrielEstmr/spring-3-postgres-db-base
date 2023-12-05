package com.challengers.accounts.gateways.kafka.resources;

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
public class KafkaTransactionMessage implements Serializable {
  @Serial private static final long serialVersionUID = -2602550833503977390L;

  private Long id;

  public KafkaTransactionMessage(final Transaction transaction) {
    this.id = transaction.getId();
  }
}
