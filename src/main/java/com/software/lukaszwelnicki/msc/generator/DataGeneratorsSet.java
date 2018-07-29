package com.software.lukaszwelnicki.msc.generator;

import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DataGeneratorsSet implements ApplicationContextAware {

    private static ApplicationContext applicationContextInstance;
    private Set<DataGenerator<Measurement>> dataGenerators;

    private Set<DataGenerator<Measurement>> fillSetWithGeneratorsAndReturnSet() {
        dataGenerators = MeasurementCollections.namesSet().stream()
                .map(getBeanByNameOrElseThrowEx())
                .map(DataGenerator::new)
                .collect(Collectors.toSet());
        return Optional.ofNullable(dataGenerators)
                .orElseThrow(RuntimeException::new);
    }

    private Function<String, Measurement> getBeanByNameOrElseThrowEx() {
        return name -> Optional.ofNullable(applicationContextInstance)
                .map(context -> ((Measurement) context.getBean(name)))
                .orElseThrow(RuntimeException::new);
    }

    public Set<DataGenerator<Measurement>> getDataGenerators() {
        return Optional.ofNullable(dataGenerators)
                .map(Collections::unmodifiableSet)
                .orElseGet(this::fillSetWithGeneratorsAndReturnSet);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContextInstance = applicationContext;
    }
}
