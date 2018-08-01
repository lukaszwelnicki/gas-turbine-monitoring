package com.software.lukaszwelnicki.msc.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.software.lukaszwelnicki.msc.generator.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private static final int MAX_RECORDS = 1000;

    private static final int SECONDS_BETWEEN_RECORDS = 15;
    private static final LocalDateTime START = LocalDateTime.now().minusDays(3);
    private static final LocalDateTime END = LocalDateTime.now();

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    private final MeasurementRepository measurementRepository;

    public void run(String... args) {
        MongoDatabase database = MongoDatabaseInstance.INSTANCE.getDatabase(host, databaseName);
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
