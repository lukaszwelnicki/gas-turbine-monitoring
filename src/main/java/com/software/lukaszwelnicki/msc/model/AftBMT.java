package com.software.lukaszwelnicki.msc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "aft_bmt")
public class AftBMT extends Measurement {

    private double temperatureOne;
    private double temperatureTwo;
    private double temperatureThree;
    private double temperatureFour;

    public AftBMT(String id,
                  Date createdDate,
                  double temperatureOne,
                  double temperatureTwo,
                  double temperatureThree,
                  double temperatureFour) {
        super(id, createdDate);
        this.temperatureOne = temperatureOne;
        this.temperatureTwo = temperatureTwo;
        this.temperatureThree = temperatureThree;
        this.temperatureFour = temperatureFour;
    }
}
