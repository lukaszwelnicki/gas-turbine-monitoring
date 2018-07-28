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
@Document(collection = CompressorEfficiency.COLLECTION_NAME)
public class CompressorEfficiency extends Measurement {
    static final String COLLECTION_NAME = "compressor_efficiency";
    private static final double MIDDLE_VALUE = 88.0;
    private static final double SPREAD = 5.0;

    private double compressorEfficiency;

    public CompressorEfficiency random() {
        return new CompressorEfficiency(RandomUtils.randomMiddleAndSpread(MIDDLE_VALUE, SPREAD));
    }
}
