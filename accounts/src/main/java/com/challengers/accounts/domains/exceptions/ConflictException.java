package com.challengers.accounts.domains.exceptions;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {

  private Object[] msgParams;

  public ConflictException(final String message) {
    super(message);
  }

  public ConflictException(final String message, final Object... params) {
    super(message);
    this.msgParams = params;
  }
}
