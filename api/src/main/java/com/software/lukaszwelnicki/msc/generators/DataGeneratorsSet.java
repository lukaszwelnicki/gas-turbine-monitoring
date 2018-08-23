package com.software.lukaszwelnicki.msc.generators;

import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
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
        dataGenerators = Stream.ofAll(MeasurementCollections.getMeasurementClasses())
                .map(c -> Try.of(c::newInstance).get())
                .map(DataGenerator::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return Collections.unmodifiableSet(dataGenerators);
    }

}
