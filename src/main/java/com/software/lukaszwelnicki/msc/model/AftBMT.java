package com.software.lukaszwelnicki.msc.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "aft_bmt")
public class AftBMT extends Measurement {

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;

    public AftBMT(double temperatureOne,
                  double temperatureTwo,
                  double temperatureThree,
                  double temperatureFour) {
        this.temperatureOne = temperatureOne;
        this.temperatureTwo = temperatureTwo;
        this.temperatureThree = temperatureThree;
        this.temperatureFour = temperatureFour;
    }
}
