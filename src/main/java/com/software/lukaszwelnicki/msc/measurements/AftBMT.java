package com.software.lukaszwelnicki.msc.measurements;


import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import io.vavr.Function2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = AftBMT.COLLECTION_NAME)
public class AftBMT extends Measurement {
    static final String COLLECTION_NAME = "aft_bmt";
    private static final double MIDDLE_VALUE = 150.0;
    private static final double SPREAD = 10.0;

    @Transient
    private final Function2<Double, Double, Double> randomTemperature =
            Function2.of(RandomUtils::randomMiddleAndSpread);

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;

    public AftBMT random() {
        return new AftBMT(
                randomTemperature.apply(MIDDLE_VALUE, SPREAD),
                randomTemperature.apply(MIDDLE_VALUE, SPREAD),
                randomTemperature.apply(MIDDLE_VALUE, SPREAD),
                randomTemperature.apply(MIDDLE_VALUE, SPREAD)
        );

    }
}
