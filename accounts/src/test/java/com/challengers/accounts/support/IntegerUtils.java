package com.challengers.accounts.support;

import java.util.Random;

public class IntegerUtils {

  public static Integer getRandomValue(final int startValue, final int range) {
    final Random rand = new Random();
    return rand.nextInt(range) + startValue;
  }
}
