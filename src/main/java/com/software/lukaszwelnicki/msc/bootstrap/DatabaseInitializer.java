package com.software.lukaszwelnicki.msc.bootstrap;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.software.lukaszwelnicki.msc.generator.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import com.software.lukaszwelnicki.msc.utils.DatabaseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private static final String DATABASE_NAME = "measurements";
    private static final String HOST = "localhost";
    private static final int MAX_RECORDS = 1000;

    private static final int SECONDS_BETWEEN_RECORDS = 15;
    private static final LocalDateTime START = LocalDateTime.now().minusDays(3);
    private static final LocalDateTime END = LocalDateTime.now();

    private final MeasurementRepository measurementRepository;
    private final MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(HOST), DATABASE_NAME);
    private final MongoDatabase database = mongoTemplate.getDb();

    public void run(String... args) {
        DatabaseUtils.dropMongoDatabase(database);
        bootstrapDB(START, END, SECONDS_BETWEEN_RECORDS);
        DatabaseUtils.convertToCapped(database, MeasurementCollections.namesSet(), MAX_RECORDS);
    }

    private void bootstrapDB(LocalDateTime start, LocalDateTime end, int secondsBetweenRecords) {
        DataGeneratorsSet.getDataGenerators().forEach(g ->
                measurementRepository
                        .saveAll(g.generateRecordsInBetweenDates(start, end, secondsBetweenRecords))
                        .subscribe());
    }
}
