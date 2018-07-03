package com.software.lukaszwelnicki.msc.model;

import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "forward_bmt")
public class ForwardBMT extends Measurement {

    private static final double MIDDLE_VALUE = 150.0;
    private static final double SPREAD = 10.0;

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;

    public ForwardBMT random() {
        return ForwardBMT.getRandom();
    }

    public static ForwardBMT getRandom() {
        ForwardBMT instance = new ForwardBMT();
        instance.setTemperatureOne(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setTemperatureTwo(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setTemperatureThree(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setTemperatureFour(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        return instance;
    }

}
