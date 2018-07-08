package com.software.lukaszwelnicki.msc.bootstrap;

import com.mongodb.MongoClient;
import com.software.lukaszwelnicki.msc.generator.DataGenerator;
import com.software.lukaszwelnicki.msc.model.*;
import com.software.lukaszwelnicki.msc.repositories.ReactiveMeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private static final int SECONDS_BETWEEN_RECORDS = 15;
    private static final LocalDateTime START = LocalDateTime.now();
    private static final LocalDateTime END = LocalDateTime.now().minusMonths(3);

    private final MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("localhost"), "measurements");

    private final ReactiveMeasurementRepository<AftBMT> aftBMTReactiveMeasurementRepository;
    private final ReactiveMeasurementRepository<CompressorEfficiency> compressorEfficiencyReactiveMeasurementRepository;
    private final ReactiveMeasurementRepository<ForwardBMT> forwardBMTReactiveMeasurementRepository;
    private final ReactiveMeasurementRepository<GeneratorVibrations> generatorVibrationsReactiveMeasurementRepository;
    private final ReactiveMeasurementRepository<TurbineEfficiency> turbineEfficiencyReactiveMeasurementRepository;
    private final ReactiveMeasurementRepository<TurbineVibrations> turbineVibrationsReactiveMeasurementRepository;

    private final Map<DataGenerator, ReactiveMeasurementRepository> dataGeneratorMeasurementRepositoryHashMap = new HashMap<>();

    @Override
    public void run(String... args) {
        fillGeneratorRepositoryMap();
        dropAllCollections();
        bootstrapDB();
    }

    private void dropAllCollections() {
        mongoTemplate.dropCollection(AftBMT.class);
        mongoTemplate.dropCollection(CompressorEfficiency.class);
        mongoTemplate.dropCollection(ForwardBMT.class);
        mongoTemplate.dropCollection(GeneratorVibrations.class);
        mongoTemplate.dropCollection(TurbineEfficiency.class);
        mongoTemplate.dropCollection(TurbineVibrations.class);
    }

    private void bootstrapDB() {
        dataGeneratorMeasurementRepositoryHashMap.forEach(((generator, repository) ->
                repository.saveAll(generator.generateRecordsInBetweenDates(START, END, SECONDS_BETWEEN_RECORDS)).subscribe()));
    }

    private void fillGeneratorRepositoryMap() {
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new AftBMT()), aftBMTReactiveMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new CompressorEfficiency()), compressorEfficiencyReactiveMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new ForwardBMT()), forwardBMTReactiveMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new GeneratorVibrations()), generatorVibrationsReactiveMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new TurbineEfficiency()), turbineEfficiencyReactiveMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new TurbineVibrations()), turbineVibrationsReactiveMeasurementRepository);
    }

}
