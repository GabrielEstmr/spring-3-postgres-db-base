package com.challengers.accounts.domains;

public enum DatabaseLockMode {
  READ,
  WRITE,
  OPTIMISTIC,
  OPTIMISTIC_FORCE_INCREMENT,
  PESSIMISTIC_READ,
  PESSIMISTIC_WRITE,
  PESSIMISTIC_FORCE_INCREMENT,
  NONE
}
