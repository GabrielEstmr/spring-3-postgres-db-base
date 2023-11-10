package com.challengers.accounts.gateways.input.api.v1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static java.util.Collections.singletonList;

@Getter
@AllArgsConstructor
public class ErrorMessage implements Serializable {

  @Serial private static final long serialVersionUID = -6675144350266764232L;

  private final List<String> errors;

  public ErrorMessage(final String error) {
    errors = singletonList(error);
  }
}
