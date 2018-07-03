package com.software.lukaszwelnicki.msc.model;

import com.software.lukaszwelnicki.msc.utils.RandomUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "turbine_efficiency")
public class TurbineEfficiency extends Measurement{

    private static final double MIDDLE_VALUE = 80.0;
    private static final double SPREAD = 5.0;

    private double turbineEfficiency;

    public TurbineEfficiency random() {
        return TurbineEfficiency.getRandom();
    }

    public static TurbineEfficiency getRandom() {
        TurbineEfficiency instance = new TurbineEfficiency();
        instance.setTurbineEfficiency(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
        return instance;
    }

}
