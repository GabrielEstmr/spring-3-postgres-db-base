package com.challengers.accounts.gateways.input.api.v1.linkbuilders;

import com.challengers.accounts.gateways.input.api.linkbuilders.BuildLinks;
import com.challengers.accounts.gateways.input.api.v1.TransactionController;
import com.challengers.accounts.gateways.input.api.v1.resources.TransactionResponse;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TransactionResponseLinkBuilder implements BuildLinks<TransactionResponse> {

  @Override
  public List<Link> build(final TransactionResponse transactionResponse) {
    final Link link =
        linkTo(
                methodOn(TransactionController.class)
                    .requestTransaction(transactionResponse.getId()))
            .withSelfRel();
    return Collections.singletonList(link);
  }
}
