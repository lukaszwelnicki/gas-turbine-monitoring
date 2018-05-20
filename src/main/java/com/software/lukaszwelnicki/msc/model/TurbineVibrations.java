package com.software.lukaszwelnicki.msc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "turbine_vibrations")
public class TurbineVibrations extends Measurement {

    private double vibrationOne;
    private double vibrationTwo;
    private double vibrationThree;
    private double vibrationFour;

    public TurbineVibrations(String id,
                             Date createdDate,
                             double vibrationOne,
                             double vibrationTwo,
                             double vibrationThree,
                             double vibrationFour) {
        super(id, createdDate);
        this.vibrationOne = vibrationOne;
        this.vibrationTwo = vibrationTwo;
        this.vibrationThree = vibrationThree;
        this.vibrationFour = vibrationFour;
    }
}
