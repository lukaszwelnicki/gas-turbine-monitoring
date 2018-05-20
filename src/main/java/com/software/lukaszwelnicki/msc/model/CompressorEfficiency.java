package com.software.lukaszwelnicki.msc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "compressor_efficiency")
public class CompressorEfficiency extends Measurement {
    private double compressorEfficiency;

    public CompressorEfficiency(String id, Date createdDate, double compressorEfficiency) {
        super(id, createdDate);
        this.compressorEfficiency = compressorEfficiency;
    }
}
