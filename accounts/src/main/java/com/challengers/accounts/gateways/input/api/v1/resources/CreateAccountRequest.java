package com.challengers.accounts.gateways.input.api.v1.resources;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateAccountRequest implements Serializable {
  @Serial private static final long serialVersionUID = -8657934975438192618L;

  @NotNull(message = "{validation.null.field}")
  @NotBlank(message = "{validation.blank.field}")
  @Size(min = 1, max = 11, message = "{validation.string.size}")
  private String document_number;
}
