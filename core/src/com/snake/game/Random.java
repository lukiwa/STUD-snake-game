package com.snake.game;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Helper class to generate random numbers
 */
public class Random {
    /**
     * Get random int but divisable by some value
     * @param min minimum random value
     * @param max maximum random value
     * @param divisor by which number the result should be divisable
     * @return random number
     */
    public static int getRandomDivisibleByNumber(int min, int max, int divisor) {
        return getRandomInt(min / divisor, max / divisor) * divisor;
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}