package com.software.lukaszwelnicki.msc.model;

import com.software.lukaszwelnicki.msc.utils.RandomUtils;
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

    private static final double MIDDLE_VALUE = 80.0;
    private static final double SPREAD = 5.0;

    private double turbineEfficiency;

    public static TurbineEfficiency random() {
        return new TurbineEfficiency(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
    }
}
