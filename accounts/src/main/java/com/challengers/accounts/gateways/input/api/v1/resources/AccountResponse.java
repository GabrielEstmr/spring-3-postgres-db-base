package com.challengers.accounts.gateways.input.api.v1.resources;

import com.challengers.accounts.domains.Account;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountResponse extends RepresentationModel<AccountResponse> implements Serializable {
  @Serial private static final long serialVersionUID = 4034748182316833099L;

  private Long account_id;
  private String document_number;

  public AccountResponse(final Account account) {
    this.account_id = account.getId();
    this.document_number = account.getDocumentNumber();
  }
}
