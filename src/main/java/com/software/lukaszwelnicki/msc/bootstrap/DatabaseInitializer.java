package com.software.lukaszwelnicki.msc.bootstrap;

import com.software.lukaszwelnicki.msc.generator.DataGenerator;
import com.software.lukaszwelnicki.msc.model.*;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    private final Map<DataGenerator, MeasurementRepository> repositoriesWithGenerators = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        fillRepositoryGeneratorMap();
        repositoriesWithGenerators.forEach((generator, repository) -> repository.deleteAll().then().block());
        bootstrapDB(LocalDateTime.now().minusDays(1), LocalDateTime.now(), 15);
    }

    private void bootstrapDB(LocalDateTime start, LocalDateTime end, int secondsBetweenRecords) {
        repositoriesWithGenerators.forEach(((generator, repository) ->
                repository.saveAll(generator.generateRecordsInBetweenDates(start, end, secondsBetweenRecords)).blockFirst()));
    }

    private void fillRepositoryGeneratorMap() {
        repositoriesWithGenerators.put(new DataGenerator<>(new AftBMT()), aftBMTMeasurementRepository);
        repositoriesWithGenerators.put(new DataGenerator<>(new CompressorEfficiency()), compressorEfficiencyMeasurementRepository);
        repositoriesWithGenerators.put(new DataGenerator<>(new ForwardBMT()), forwardBMTMeasurementRepository);
        repositoriesWithGenerators.put(new DataGenerator<>(new GeneratorVibrations()), generatorVibrationsMeasurementRepository);
        repositoriesWithGenerators.put(new DataGenerator<>(new TurbineEfficiency()), turbineEfficiencyMeasurementRepository);
        repositoriesWithGenerators.put(new DataGenerator<>(new TurbineVibrations()), turbineVibrationsMeasurementRepository);
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
