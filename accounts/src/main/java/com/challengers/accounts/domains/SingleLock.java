package com.challengers.accounts.domains;

import com.challengers.accounts.gateways.lock.resources.SingleLockResource;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingleLock {

  private final SingleLockResource singleLockResource;

    public boolean tryLock() {
        return singleLockResource.tryLock();
    }

    public void unlock() {
        this.singleLockResource.unlock();
    }
}
