package com.software.lukaszwelnicki.msc.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "forward_bmt")
public class ForwardBMT extends Measurement {

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;

    public ForwardBMT(double temperatureOne,
                      double temperatureTwo,
                      double temperatureThree,
                      double temperatureFour) {
        this.temperatureOne = temperatureOne;
        this.temperatureTwo = temperatureTwo;
        this.temperatureThree = temperatureThree;
        this.temperatureFour = temperatureFour;
    }
}
