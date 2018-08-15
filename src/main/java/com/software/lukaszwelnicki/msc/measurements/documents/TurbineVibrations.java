package com.software.lukaszwelnicki.msc.measurements.documents;

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
@Document(collection = TurbineVibrations.COLLECTION_NAME)
public class TurbineVibrations extends Measurement {

    public static final String COLLECTION_NAME = "turbine_vibrations";
    private static final double MIDDLE_VALUE = 2.0;
    private static final double SPREAD = 1.5;

    private double vibrationOne;
    private double vibrationTwo;
    private double vibrationThree;
    private double vibrationFour;

    public TurbineVibrations random() {
        return new TurbineVibrations(
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD)
        );
    }
}
