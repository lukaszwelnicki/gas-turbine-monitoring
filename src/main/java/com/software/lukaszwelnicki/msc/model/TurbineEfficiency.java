package com.software.lukaszwelnicki.msc.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "turbine_efficiency")
public class TurbineEfficiency extends Measurement{
    private double turbineEfficiency;

    public TurbineEfficiency(double turbineEfficiency) {
        this.turbineEfficiency = turbineEfficiency;
    }
}
