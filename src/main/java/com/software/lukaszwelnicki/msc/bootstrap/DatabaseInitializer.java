package com.software.lukaszwelnicki.msc.bootstrap;

import com.software.lukaszwelnicki.msc.generator.DataGenerator;
import com.software.lukaszwelnicki.msc.model.*;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final MeasurementRepository<AftBMT> aftBMTMeasurementRepository;
    private final MeasurementRepository<CompressorEfficiency> compressorEfficiencyMeasurementRepository;
    private final MeasurementRepository<ForwardBMT> forwardBMTMeasurementRepository;
    private final MeasurementRepository<GeneratorVibrations> generatorVibrationsMeasurementRepository;
    private final MeasurementRepository<TurbineEfficiency> turbineEfficiencyMeasurementRepository;
    private final MeasurementRepository<TurbineVibrations> turbineVibrationsMeasurementRepository;

    private final Map<MeasurementRepository, DataGenerator> repositoriesWithGenerators = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        fillRepositoryGeneratorMap();
        repositoriesWithGenerators.forEach((repository, generator) -> repository.deleteAll().block());
        bootstrapDB(LocalDateTime.now().minusMonths(3), LocalDateTime.now(), 15);
        log.info("Finished bootstrap. Number of recorts in one table: " + aftBMTMeasurementRepository.findAll().count().block());
    }

    private void bootstrapDB(LocalDateTime start, LocalDateTime end, int secondsBetweenRecords) {
        repositoriesWithGenerators.forEach(((repository, generator) ->
                repository.saveAll(generator.generateRecordsInBetweenDates(start, end, secondsBetweenRecords))));
    }

    private void fillRepositoryGeneratorMap() {
        repositoriesWithGenerators.put(aftBMTMeasurementRepository, new DataGenerator<>(new AftBMT()));
        repositoriesWithGenerators.put(compressorEfficiencyMeasurementRepository, new DataGenerator<>(new CompressorEfficiency()));
        repositoriesWithGenerators.put(forwardBMTMeasurementRepository, new DataGenerator<>(new ForwardBMT()));
        repositoriesWithGenerators.put(generatorVibrationsMeasurementRepository, new DataGenerator<>(new GeneratorVibrations()));
        repositoriesWithGenerators.put(turbineEfficiencyMeasurementRepository, new DataGenerator<>(new TurbineEfficiency()));
        repositoriesWithGenerators.put(turbineVibrationsMeasurementRepository, new DataGenerator<>(new TurbineVibrations()));
    }

    public DatabaseInitializer(MeasurementRepository<AftBMT> aftBMTMeasurementRepository,
                               MeasurementRepository<CompressorEfficiency> compressorEfficiencyMeasurementRepository,
                               MeasurementRepository<ForwardBMT> forwardBMTMeasurementRepository,
                               MeasurementRepository<GeneratorVibrations> generatorVibrationsMeasurementRepository,
                               MeasurementRepository<TurbineEfficiency> turbineEfficiencyMeasurementRepository,
                               MeasurementRepository<TurbineVibrations> turbineVibrationsMeasurementRepository) {
        this.aftBMTMeasurementRepository = aftBMTMeasurementRepository;
        this.compressorEfficiencyMeasurementRepository = compressorEfficiencyMeasurementRepository;
        this.forwardBMTMeasurementRepository = forwardBMTMeasurementRepository;
        this.generatorVibrationsMeasurementRepository = generatorVibrationsMeasurementRepository;
        this.turbineEfficiencyMeasurementRepository = turbineEfficiencyMeasurementRepository;
        this.turbineVibrationsMeasurementRepository = turbineVibrationsMeasurementRepository;
    }
}
