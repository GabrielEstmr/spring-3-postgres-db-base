package com.challengers.accounts.domains.exceptions;

import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {

  private Object[] msgParams;

  public InternalServerErrorException(final String message) {
    super(message);
  }

  public InternalServerErrorException(final String message, final Object... params) {
    super(message);
    this.msgParams = params;
  }
}
