package com.software.lukaszwelnicki.msc.measurements;

import com.software.lukaszwelnicki.msc.measurements.documents.*;

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
    Class<? extends Measurement> clazz;

    MeasurementCollections(String collectionName, Class<? extends Measurement> clazz) {
        this.collectionName = collectionName;
        this.clazz = clazz;
    }

    public static Set<Class<? extends Measurement>> getMeasurementClasses() {
        return Arrays.stream(MeasurementCollections.values())
                .map(MeasurementCollections::getClazz)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }

    public static Set<String> namesSet() {
        return Arrays.stream(MeasurementCollections.values())
                .map(MeasurementCollections::getCollectionName)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
    }

    public static Class<? extends Measurement> findMeasurementClassByCollectionName(String name) {
        return Arrays.stream(MeasurementCollections.values())
                .filter(m -> name.equals(m.getCollectionName()))
                .findFirst()
                .map(MeasurementCollections::getClazz)
                .orElse(null);
    }

    private String getCollectionName() {
        return collectionName;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Measurement> getClazz() {
        return clazz;
    }
}
