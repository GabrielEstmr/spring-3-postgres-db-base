package com.challengers.accounts.gateways.lock;

import com.challengers.accounts.domains.SingleLock;
import com.challengers.accounts.gateways.lock.resources.SingleLockResource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LockClientGatewayImpl implements LockClientGateway {

  @Qualifier("lockRegistry")
  private final ExpirableLockRegistry lockRegistry;

  @Override
  public SingleLock getLock(final String lockKey) {
    return new SingleLockResource(lockRegistry.obtain(lockKey)).toDomain();
  }
}
