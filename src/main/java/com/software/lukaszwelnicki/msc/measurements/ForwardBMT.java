package com.software.lukaszwelnicki.msc.measurements;

import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import io.vavr.Function2;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = ForwardBMT.COLLECTION_NAME)
public class ForwardBMT extends Measurement {

    static final String COLLECTION_NAME = "forward_bmt";
    private static final double MIDDLE_VALUE = 150.0;
    private static final double SPREAD = 10.0;

    @Transient
    private final Function2<Double, Double, Double> randomTemperature =
            Function2.of(RandomUtils::randomMiddleAndSpread);

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;


    public ForwardBMT random() {
        return new ForwardBMT(
                randomTemperature.apply(MIDDLE_VALUE, SPREAD),
                randomTemperature.apply(MIDDLE_VALUE, SPREAD),
                randomTemperature.apply(MIDDLE_VALUE, SPREAD),
                randomTemperature.apply(MIDDLE_VALUE, SPREAD)
        );
    }
}
