package com.software.lukaszwelnicki.msc.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "compressor_efficiency")
public class CompressorEfficiency extends Measurement {
    private static final double MIDDLE_VALUE = 88.0;
    private static final double SPREAD =

    private double compressorEfficiency;

    public CompressorEfficiency(double compressorEfficiency) {
        this.compressorEfficiency = compressorEfficiency;
    }
}
