package com.challengers.accounts.domains.exceptions;

import java.io.Serial;

public class JsonUtilsException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -6476486089759370591L;

  public JsonUtilsException(final Exception cause) {
    super(cause);
  }

  public JsonUtilsException(final String message, final Exception cause) {
    super(message, cause);
  }
}
