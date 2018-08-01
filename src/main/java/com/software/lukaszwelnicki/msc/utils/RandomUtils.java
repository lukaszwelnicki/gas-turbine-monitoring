package com.software.lukaszwelnicki.msc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RandomUtils {
    private static final Random RANDOM = new Random();

    public static double randomMiddleAndSpread(double middleValue, double spread) {
        if (spread < 0) {
            throw new IllegalArgumentException("Spread value should be greater than 0.");
        }
        double randomDouble = (RANDOM.nextDouble() * spread) + middleValue - spread/2;
        BigDecimal bd = BigDecimal.valueOf(randomDouble).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
