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
@Document(collection = GeneratorVibrations.COLLECTION_NAME)
public class GeneratorVibrations extends Measurement {

    static final String COLLECTION_NAME = "generator_vibrations";
    private static final double MIDDLE_VALUE = 2.0;
    private static final double SPREAD = 1.5;

    @Transient
    private final Function2<Double, Double, Double> randomVibrations =
            Function2.of(RandomUtils::randomMiddleAndSpread);

    private double vibrationOne;
    private double vibrationTwo;
    private double vibrationThree;
    private double vibrationFour;

    public GeneratorVibrations random() {
        return new GeneratorVibrations(
                randomVibrations.apply(MIDDLE_VALUE, SPREAD),
                randomVibrations.apply(MIDDLE_VALUE, SPREAD),
                randomVibrations.apply(MIDDLE_VALUE, SPREAD),
                randomVibrations.apply(MIDDLE_VALUE, SPREAD)
        );
    }
}
