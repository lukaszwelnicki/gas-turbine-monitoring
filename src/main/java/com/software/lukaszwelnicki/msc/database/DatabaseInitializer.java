package com.software.lukaszwelnicki.msc.database;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.Success;
import com.software.lukaszwelnicki.msc.config.YAMLConfig;
import com.software.lukaszwelnicki.msc.generators.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
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

    private static final LocalDateTime START = LocalDateTime.now().minusDays(3);
    private static final LocalDateTime END = LocalDateTime.now();

    private final DatabaseUtils databaseUtils;
    private final MeasurementRepository<Measurement> measurementRepository;
    private final ReactiveMongoTemplate mongoTemplate;
    private final YAMLConfig yamlConfig;

    public void run(String... args) {
        dropDatabase()
                .thenMany(createCollections())
                .thenMany(bootstrapDb())
                .subscribe();
    }

    private Flux<MongoCollection<Document>> createCollections() {
        return Flux.fromIterable(MeasurementCollections.getMeasurementClasses())
                .flatMap(databaseUtils::createCappedCollectionByClass);
    }

    private Mono<Success> dropDatabase() {
        return Mono.from(mongoTemplate.getMongoDatabase().drop());
    }

    private Flux<? extends Measurement> bootstrapDb() {
        return Flux.fromIterable(DataGeneratorsSet.INSTANCE.getDataGenerators())
                .flatMap(g -> measurementRepository
                        .saveAll(g.generateRecordsInBetweenDates(START, END, yamlConfig.getSamplingSeconds())));
    }


}
