package com.software.lukaszwelnicki.msc.bootstrap;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.software.lukaszwelnicki.msc.generator.DataGenerator;
import com.software.lukaszwelnicki.msc.measurements.*;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
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
    private static final LocalDateTime START = LocalDateTime.now().minusDays(3);
    private static final LocalDateTime END = LocalDateTime.now();

    private final MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("localhost"), "measurements");

    private final MeasurementRepository<AftBMT> aftBMTMeasurementRepository;
    private final MeasurementRepository<CompressorEfficiency> compressorEfficiencyMeasurementRepository;
    private final MeasurementRepository<ForwardBMT> forwardBMTMeasurementRepository;
    private final MeasurementRepository<GeneratorVibrations> generatorVibrationsMeasurementRepository;
    private final MeasurementRepository<TurbineEfficiency> turbineEfficiencyMeasurementRepository;
    private final MeasurementRepository<TurbineVibrations> turbineVibrationsMeasurementRepository;

    private final Map<DataGenerator, MeasurementRepository> dataGeneratorMeasurementRepositoryHashMap = new HashMap<>();

    @Override
    public void run(String... args) {
        fillGeneratorRepositoryMap();
        dropAllCollections();
        bootstrapDB();
        convertCollectionsToCapped();
    }

    private void fillGeneratorRepositoryMap() {
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new AftBMT()), aftBMTMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new CompressorEfficiency()), compressorEfficiencyMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new TurbineVibrations()), turbineVibrationsMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new ForwardBMT()), forwardBMTMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new GeneratorVibrations()), generatorVibrationsMeasurementRepository);
        dataGeneratorMeasurementRepositoryHashMap.put(new DataGenerator<>(new TurbineEfficiency()), turbineEfficiencyMeasurementRepository);
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
        dataGeneratorMeasurementRepositoryHashMap.forEach((generator, repository) ->
                repository.saveAll(generator.generateRecordsInBetweenDates(START, END, SECONDS_BETWEEN_RECORDS)).subscribe());
    }

    private void convertCollectionsToCapped() {
        final MongoDatabase db = mongoTemplate.getDb();
    }

}
