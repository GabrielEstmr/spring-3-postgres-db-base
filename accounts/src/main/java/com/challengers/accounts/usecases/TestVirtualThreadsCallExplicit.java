package com.challengers.accounts.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestVirtualThreadsCallExplicit {

  public void execute(final List<Integer> awaiters) {
    log.info("START");
    final var executor = Executors.newVirtualThreadPerTaskExecutor();
    awaiters.forEach(el -> executor.submit(()->{
      try {
        Thread.sleep(el * 1000);
        System.out.println("Hello from a virtual thread EXPLICIT!" + el);
        System.out.println("CURRENT THREAD" + Thread.currentThread());
      } catch (InterruptedException e) {
        log.debug("ERROR from a virtual thread EXPLICIT!" + e.getMessage());
      }
      return el;
    }));
    executor.shutdown();
    log.info("FINAL");
  }

  private Supplier<Integer> buildFunc(final Integer period) {
    return () -> {
      try {
        Thread.sleep(period * 1000);
        System.out.println("Hello from a virtual thread EXPLICIT!" + period);
      } catch (InterruptedException e) {
        log.debug("ERROR from a virtual thread EXPLICIT!" + e.getMessage());
      }
      return period;
    };
  }
}
