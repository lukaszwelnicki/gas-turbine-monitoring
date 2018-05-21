package com.software.lukaszwelnicki.msc.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "generator_vibrations")
public class GeneratorVibrations extends Measurement {

    private double vibrationOne;
    private double vibrationTwo;
    private double vibrationThree;
    private double vibrationFour;

    public GeneratorVibrations( double vibrationOne,
                                double vibrationTwo,
                                double vibrationThree,
                                double vibrationFour) {
        this.vibrationOne = vibrationOne;
        this.vibrationTwo = vibrationTwo;
        this.vibrationThree = vibrationThree;
        this.vibrationFour = vibrationFour;
    }
}
