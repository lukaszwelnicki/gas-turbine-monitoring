package com.software.lukaszwelnicki.msc.model;

import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "turbine_vibrations")
public class TurbineVibrations extends Measurement {

    private static final double MIDDLE_VALUE = 2.0;
    private static final double SPREAD = 1.5;

    private double vibrationOne;
    private double vibrationTwo;
    private double vibrationThree;
    private double vibrationFour;

    public TurbineVibrations random() {
        return TurbineVibrations.getRandom();
    }

    public static TurbineVibrations getRandom() {
        TurbineVibrations instance = new TurbineVibrations();
        instance.setVibrationOne(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setVibrationTwo(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setVibrationThree(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        instance.setVibrationFour(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        return instance;
    }

}
