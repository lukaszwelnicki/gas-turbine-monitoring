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
@Document(collection = AftBMT.COLLECTION_NAME)
public class AftBMT extends Measurement {
    public static final String COLLECTION_NAME = "aft_bmt";
    private static final double MIDDLE_VALUE = 150.0;
    private static final double SPREAD = 10.0;

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;

    public AftBMT random() {
        return new AftBMT(
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD)
        );

    }
}
