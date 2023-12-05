package com.challengers.accounts.gateways.api.v1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import static java.util.Collections.singletonList;

@Getter
@AllArgsConstructor
public class ErrorMessage implements Serializable {

  @Serial private static final long serialVersionUID = -6675144350266764232L;

  private final String code;
  private final List<String> errors;

  public ErrorMessage(final String code, final String error) {
    this.code = code;
    this.errors = singletonList(error);
  }
}
