package com.software.lukaszwelnicki.msc.measurements.documents;

import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = ForwardBMT.COLLECTION_NAME)
public class ForwardBMT extends Measurement {

    public static final String COLLECTION_NAME = "forward_bmt";
    private static final double MIDDLE_VALUE = 150.0;
    private static final double SPREAD = 10.0;

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;


    public ForwardBMT random() {
        return new ForwardBMT(
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE - 5, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE - 10, SPREAD),
                RandomUtils.randomMiddleAndSpreadFunction().apply(MIDDLE_VALUE - 15, SPREAD)
        );
    }
}
