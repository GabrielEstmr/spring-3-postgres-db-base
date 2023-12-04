package com.challengers.accounts.gateways.lock.resources;

import com.challengers.accounts.domains.SingleLock;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.locks.Lock;

@RequiredArgsConstructor
public class SingleLockResource {

  private final Lock lock;

  public boolean tryLock() {
    return lock.tryLock();
  }

  public void unlock() {
    this.lock.unlock();
  }

  public SingleLock toDomain() {
    return new SingleLock(this);
  }
}
