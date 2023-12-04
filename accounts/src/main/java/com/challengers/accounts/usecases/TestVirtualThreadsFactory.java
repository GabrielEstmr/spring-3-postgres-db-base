package com.challengers.accounts.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestVirtualThreadsFactory {

  private final TestVirtualThreadsCallExplicit testVirtualThreadsCallExplicit;
  private final TestVirtualThreadsUsingAsyncAnnotation testVirtualThreadsUsingAsyncAnnotation;

  public void execute() {
    final var listExec =
        Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    testVirtualThreadsCallExplicit.execute(listExec);
    listExec.forEach(testVirtualThreadsUsingAsyncAnnotation::execute);
  }
}
