package com.challengers.accounts.gateways.input.api.v1.resources;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTransactionRequest {

  @NotNull(message = "{validation.null.field}")
  private Long account_id;

  @NotNull(message = "{validation.null.field}")
  @Min(value = 1, message = "{validation.bigger.than.zero.field}")
  private Integer operation_type_id;

  @NotNull(message = "{validation.null.field}")
  @DecimalMin(value = "0.0", inclusive = false, message = "{validation.bigger.than.zero.field}")
  @Digits(integer = 13, fraction = 2, message = "{validation.decimal.numeric}")
  private BigDecimal amount;
}
