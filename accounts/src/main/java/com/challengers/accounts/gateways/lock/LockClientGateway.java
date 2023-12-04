package com.challengers.accounts.gateways.lock;

import com.challengers.accounts.domains.SingleLock;

public interface LockClientGateway {
    SingleLock getLock(final String lockKey);
}
