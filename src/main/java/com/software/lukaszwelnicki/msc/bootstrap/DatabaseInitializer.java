package com.software.lukaszwelnicki.msc.bootstrap;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.software.lukaszwelnicki.msc.generator.DataGenerator;
import com.software.lukaszwelnicki.msc.generator.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import com.software.lukaszwelnicki.msc.utils.DatabaseUtils;
import io.vavr.Function1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private static final int SECONDS_BETWEEN_RECORDS = 15;
    private static final LocalDateTime START = LocalDateTime.now().minusDays(3);
    private static final LocalDateTime END = LocalDateTime.now();
    private static final String DATABASE_NAME = "measurements";
    private static final String HOST = "localhost";

    private final MeasurementRepository measurementRepository;
    private final DataGeneratorsSet dataGeneratorsSet;

    private final MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(HOST), DATABASE_NAME);
    private final MongoDatabase database = mongoTemplate.getDb();
    private final Function1<String, Document> convertCollectionToCapped = Function1.of(DatabaseUtils::)

    @Override
    public void run(String... args) {
        dropDatabase();
        bootstrapDB();
//        convertCollectionsToCapped();
    }

    private void dropDatabase() {
        database.runCommand(new Document("dropDatabase", 1));
    }

    private void bootstrapDB() {
        dataGeneratorsSet.getDataGenerators().forEach(g ->
                measurementRepository.saveAll(g.generateRecordsInBetweenDates(START, END, SECONDS_BETWEEN_RECORDS)).subscribe());
    }

    private Document createConvertToCappedCommandForCollectionName(String collectionName) {
        return new Document("convertToCapped", collectionName)
    }

    private void convertCollectionsToCapped() {
        MeasurementCollections.namesSet().stream()
                .map(name -> database.runCommand())
    }

}
