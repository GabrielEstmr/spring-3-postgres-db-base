package com.challengers.accounts.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestVirtualThreadsUsingAsyncAnnotation {

  @Async
  public void execute(final Integer awaiters) {
    log.info("START");
    try {
      Thread.sleep(1000L * awaiters);
      System.out.println("Hello from a virtual thread ASYNC!" + awaiters);
      System.out.println("CURRENT THREAD" + Thread.currentThread());
    } catch (InterruptedException e) {
      log.debug("ERROR from a virtual thread ASYNC!" + e.getMessage());
    }
    log.info("FINAL");
  }

  @Scheduled(fixedDelayString = "15000")
  public void execute2(final Integer awaiters) {
    log.info("START");
    try {
      Thread.sleep(1000L * awaiters);
      System.out.println("Hello from a virtual thread ASYNC!" + awaiters);
      System.out.println("CURRENT THREAD" + Thread.currentThread());
    } catch (InterruptedException e) {
      log.debug("ERROR from a virtual thread ASYNC!" + e.getMessage());
    }
    log.info("FINAL");
  }
}
