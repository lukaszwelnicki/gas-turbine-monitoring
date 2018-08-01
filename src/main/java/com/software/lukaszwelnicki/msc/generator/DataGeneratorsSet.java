package com.software.lukaszwelnicki.msc.generator;

import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class DataGeneratorsSet {

    private static Set<DataGenerator<Measurement>> dataGenerators;

    public static Set<DataGenerator<Measurement>> getDataGenerators() {
        return Optional.ofNullable(dataGenerators)
                .map(Collections::unmodifiableSet)
                .orElseGet(DataGeneratorsSet::prepareDataGenerators);
    }

    private static Set<DataGenerator<Measurement>> prepareDataGenerators() {
        dataGenerators = MeasurementCollections.getMeasurementClasses().stream()
                .map(instantiateMeasurementClass())
                .map(DataGenerator::new)
                .collect(Collectors.toSet());
        return Collections.unmodifiableSet(dataGenerators);
    }

    private static Function<Class<Measurement>, Measurement> instantiateMeasurementClass() {
        return c -> {
            try {
                return c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not instantiate class: " + c.getCanonicalName());
            }
        };
    }

}
