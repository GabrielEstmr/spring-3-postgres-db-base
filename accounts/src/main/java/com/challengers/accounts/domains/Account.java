package com.challengers.accounts.domains;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Account implements Serializable {
  @Serial private static final long serialVersionUID = -7956323375826962246L;

  private Long id;
  private String documentNumber;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public Account(final String documentNumber) {
    this.documentNumber = documentNumber;
  }
}
