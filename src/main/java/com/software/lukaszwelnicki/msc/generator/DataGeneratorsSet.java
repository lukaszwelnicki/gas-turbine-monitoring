package com.software.lukaszwelnicki.msc.generator;

import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DataGeneratorsSet {
    INSTANCE;

    private Set<DataGenerator<? extends Measurement>> dataGenerators;

    public Set<DataGenerator<? extends Measurement>> getDataGenerators() {
        return Optional.ofNullable(dataGenerators)
                .map(Collections::unmodifiableSet)
                .orElseGet(this::prepareDataGenerators);
    }

    private Set<DataGenerator<? extends Measurement>> prepareDataGenerators() {
        dataGenerators = MeasurementCollections.getMeasurementClasses().stream()
                .map(instantiateMeasurementClass())
                .map(DataGenerator::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return Collections.unmodifiableSet(dataGenerators);
    }

    private Function<Class<? extends Measurement>, ? extends Measurement> instantiateMeasurementClass() {
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
