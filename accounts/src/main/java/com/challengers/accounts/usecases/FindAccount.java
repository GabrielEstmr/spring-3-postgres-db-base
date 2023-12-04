package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.domains.SingleLock;
import com.challengers.accounts.domains.exceptions.ResourceNotFoundException;
import com.challengers.accounts.gateways.lock.LockClientGateway;
import com.challengers.accounts.gateways.output.AccountDatabaseGateway;
import com.challengers.accounts.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FindAccount {

  private static final String MSG_ERROR_ACCOUNT_NOT_FOUND = "there.is.no.account.for.given.id";

  @Qualifier("accountCachedDatabaseGatewayImpl")
  private final AccountDatabaseGateway accountDatabaseGateway;

  private final LockClientGateway lockClientGateway;

  private final MessageUtils messageUtils;

  public Account execute(final Long accountId) {
    log.debug("Requesting Account with id: {}", accountId);
    final SingleLock lock = lockClientGateway.getLock(accountId.toString());
    Account account = new Account("null");
    if (lock.tryLock()) {
      try {
        return accountDatabaseGateway
            .findById(accountId)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        messageUtils.getMessageDefaultLocale(MSG_ERROR_ACCOUNT_NOT_FOUND)));
      } catch (final Exception e) {
        log.info(e.getMessage());
        throw e;
      } finally {
        lock.unlock();
      }
    } else {
      log.warn("Requesting Account already locked. Id: {}", accountId);
    }
    return account;
  }
}
