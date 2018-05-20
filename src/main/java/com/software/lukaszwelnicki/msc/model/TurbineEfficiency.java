package com.software.lukaszwelnicki.msc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "turbine_efficiency")
public class TurbineEfficiency extends Measurement{
    private double turbineEfficiency;

    public TurbineEfficiency(String id, Date createdDate, double turbineEfficiency) {
        super(id, createdDate);
        this.turbineEfficiency = turbineEfficiency;
    }
}
