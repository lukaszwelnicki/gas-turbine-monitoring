package com.software.lukaszwelnicki.msc.measurements;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum MeasurementCollections {
    AFT_BMT(AftBMT.COLLECTION_NAME),
    COMPRESSOR_EFFICIENCY(CompressorEfficiency.COLLECTION_NAME),
    FORWARD_BMT(ForwardBMT.COLLECTION_NAME),
    GENERATOR_VIBRATIONS(GeneratorVibrations.COLLECTION_NAME),
    TURBINE_EFFICIENCY(TurbineEfficiency.COLLECTION_NAME),
    TURBINE_VIBRATIONS(TurbineVibrations.COLLECTION_NAME);

    String collectionName;

    MeasurementCollections(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public Set<String> namesSet() {
        return Arrays.stream(MeasurementCollections.values())
                .map(MeasurementCollections::getCollectionName)
                .collect(Collectors.toSet());
    }
}
