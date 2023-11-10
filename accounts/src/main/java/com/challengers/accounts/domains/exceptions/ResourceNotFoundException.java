package com.challengers.accounts.domains.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

  private Object[] msgParams;

  public ResourceNotFoundException(final String message) {
    super(message);
  }

  public ResourceNotFoundException(final String message, final Object... params) {
    super(message);
    this.msgParams = params;
  }
}
