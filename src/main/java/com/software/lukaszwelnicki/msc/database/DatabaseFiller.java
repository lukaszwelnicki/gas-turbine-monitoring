package com.software.lukaszwelnicki.msc.database;

import com.software.lukaszwelnicki.msc.config.YAMLConfig;
import com.software.lukaszwelnicki.msc.generators.DataGenerator;
import com.software.lukaszwelnicki.msc.generators.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseFiller {

    private final MeasurementRepository<Measurement> measurementRepository;
    private final YAMLConfig yamlConfig;

    private final Set<DataGenerator<? extends Measurement>> generators = DataGeneratorsSet.INSTANCE.getDataGenerators();

    public Flux<? extends Measurement> fillDatabase() {
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(yamlConfig.getSamplingSeconds()))
                .flatMap(l -> Flux.fromIterable(generators))
                .flatMap(g -> measurementRepository.save(g.generateRandomRecord()))
                .log()
                .share();
    }

}
