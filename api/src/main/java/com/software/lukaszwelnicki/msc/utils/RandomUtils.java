package com.software.lukaszwelnicki.msc.utils;

import io.vavr.Function2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static double randomMiddleAndSpread(double middleValue, double spread) {
        if (spread <= 0) {
            throw new IllegalArgumentException("Spread value should be greater than 0.");
        }
        double randomDouble = (ThreadLocalRandom.current().nextDouble(middleValue - spread / 2, middleValue + spread / 2));
        BigDecimal bd = BigDecimal.valueOf(randomDouble).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Function2<Double, Double, Double> randomMiddleAndSpreadFunction() {
        return Function2.of(RandomUtils::randomMiddleAndSpread);
    }
}
