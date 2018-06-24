package com.software.lukaszwelnicki.msc.model;

import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "aft_bmt")
public class AftBMT extends Measurement {

    private static final double MIDDLE_VALUE = 150.0;
    private static final double SPREAD = 10.0;

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;

    public static AftBMT random() {
        double temperatureOne = RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD);
        double temperatureTwo = RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD);
        double temperatureThree = RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD);
        double temperatureFour = RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD);
        return new AftBMT(temperatureOne, temperatureTwo, temperatureThree, temperatureFour);
    }
}
