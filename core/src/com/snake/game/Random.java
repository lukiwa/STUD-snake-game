package com.snake.game;

import java.util.concurrent.ThreadLocalRandom;

public class Random {
    public static int getRandomDivisibleByNumber(int min, int max, int divisor) {
        return getRandomInt(min / divisor, max / divisor) * divisor;
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}