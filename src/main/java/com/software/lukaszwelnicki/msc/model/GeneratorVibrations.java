package com.software.lukaszwelnicki.msc.model;

import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "generator_vibrations")
public class GeneratorVibrations extends Measurement {

    private static final double MIDDLE_VALUE = 2.0;
    private static final double SPREAD = 1.5;

    private double vibrationOne;
    private double vibrationTwo;
    private double vibrationThree;
    private double vibrationFour;

    public static GeneratorVibrations random() {
        double vibrationOne = RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD);
        double vibrationTwo = RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD);
        double vibrationThree = RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD);
        double vibrationFour = RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD);
        return new GeneratorVibrations(vibrationOne, vibrationTwo, vibrationThree, vibrationFour);
    }
}
