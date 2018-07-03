package com.software.lukaszwelnicki.msc.model;


import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "forward_bmt")
public class AftBMT extends Measurement {

    private static final double MIDDLE_VALUE = 150.0;
    private static final double SPREAD = 10.0;

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;

    public AftBMT random() {
        return AftBMT.getRandom();
    }

    public static AftBMT getRandom() {
        AftBMT instance = new AftBMT();
        instance.setTemperatureOne(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setTemperatureTwo(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setTemperatureThree(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setTemperatureFour(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        return instance;
    }
}
