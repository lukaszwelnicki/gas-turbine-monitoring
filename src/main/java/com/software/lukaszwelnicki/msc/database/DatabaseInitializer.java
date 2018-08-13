package com.software.lukaszwelnicki.msc.database;

import com.mongodb.client.MongoDatabase;
import com.software.lukaszwelnicki.msc.generator.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private static final int MAX_RECORDS = 1000000;

    private static final int SECONDS_BETWEEN_RECORDS = 15;
    private static final LocalDateTime START = LocalDateTime.now().minusDays(3);
    private static final LocalDateTime END = LocalDateTime.now();

    private final DatabaseUtils databaseUtils;
    private final MeasurementRepository<Measurement> measurementRepository;

    @Qualifier(DatabaseConfig.TEMPLATE_NAME)
    private final ReactiveMongoTemplate mongoTemplate;

    public void run(String... args) {
        dropAllCollections()
                .thenMany(convertAllCollectionsToCapped())
                .thenMany(bootstrapDb())
                .subscribe();
    }

    private Flux<? extends Measurement> bootstrapDb() {
         return Flux.fromIterable(DataGeneratorsSet.INSTANCE.getDataGenerators())
                .flatMap(g -> measurementRepository
                        .saveAll(g.generateRecordsInBetweenDates(START, END, SECONDS_BETWEEN_RECORDS)));
    }

    private Flux<Document> convertAllCollectionsToCapped() {
        return databaseUtils.convertCollectionsToCapped(MAX_RECORDS);
    }

    private Flux<Void> dropAllCollections() {
        return Flux.fromIterable(MeasurementCollections.getMeasurementClasses())
                .flatMap(mongoTemplate::dropCollection);
    }
}
