package com.challengers.accounts.gateways.input.api.v1.linkbuilders;

import com.challengers.accounts.gateways.input.api.linkbuilders.BuildLinks;
import com.challengers.accounts.gateways.input.api.v1.AccountController;
import com.challengers.accounts.gateways.input.api.v1.resources.AccountResponse;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountResponseLinkBuilder implements BuildLinks<AccountResponse> {

  @Override
  public List<Link> build(final AccountResponse accountResponse) {
    final Link link =
        linkTo(methodOn(AccountController.class).requestAccount(accountResponse.getAccount_id()))
            .withSelfRel();
    return Collections.singletonList(link);
  }
}
