package com.software.lukaszwelnicki.msc.measurements;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public enum MeasurementCollections {
    AFT_BMT(AftBMT.COLLECTION_NAME, AftBMT.class),
    COMPRESSOR_EFFICIENCY(CompressorEfficiency.COLLECTION_NAME, CompressorEfficiency.class),
    FORWARD_BMT(ForwardBMT.COLLECTION_NAME, ForwardBMT.class),
    GENERATOR_VIBRATIONS(GeneratorVibrations.COLLECTION_NAME, GeneratorVibrations.class),
    TURBINE_EFFICIENCY(TurbineEfficiency.COLLECTION_NAME, TurbineEfficiency.class),
    TURBINE_VIBRATIONS(TurbineVibrations.COLLECTION_NAME, TurbineVibrations.class);

    String collectionName;
    Class clazz;

    MeasurementCollections(String collectionName, Class clazz) {
        this.collectionName = collectionName;
        this.clazz = clazz;
    }

    public static Set<Class<Measurement>> beanClasses() {
        return Arrays.stream(MeasurementCollections.values())
                .map(MeasurementCollections::getClazz)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }

    private String getCollectionName() {
        return collectionName;
    }

    public static Set<String> namesSet() {
        return Arrays.stream(MeasurementCollections.values())
                .map(MeasurementCollections::getCollectionName)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }

    @SuppressWarnings("unchecked")
    private Class<Measurement> getClazz() {
        return clazz;
    }
}
