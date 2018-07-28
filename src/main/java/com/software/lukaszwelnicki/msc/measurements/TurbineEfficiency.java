package com.software.lukaszwelnicki.msc.measurements;

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
@Document(collection = TurbineEfficiency.COLLECTION_NAME)
public class TurbineEfficiency extends Measurement{

    static final String COLLECTION_NAME = "turbine_efficiency";
    private static final double MIDDLE_VALUE = 80.0;
    private static final double SPREAD = 5.0;

    private double turbineEfficiency;

    public TurbineEfficiency random() {
        return new TurbineEfficiency(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
    }
}
