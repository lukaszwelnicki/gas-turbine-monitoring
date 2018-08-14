package com.software.lukaszwelnicki.msc.database;

import com.software.lukaszwelnicki.msc.YAMLConfig.YAMLConfig;
import com.software.lukaszwelnicki.msc.generator.DataGeneratorsSet;
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
        dropAllCollections()
                .thenMany(bootstrapDb())
                .thenMany(convertAllCollectionsToCapped())
                .subscribe();
    }

    private Flux<? extends Measurement> bootstrapDb() {
         return Flux.fromIterable(DataGeneratorsSet.INSTANCE.getDataGenerators())
                .flatMap(g -> measurementRepository
                        .saveAll(g.generateRecordsInBetweenDates(START, END, yamlConfig.getSamplingSeconds())));
    }

    private Flux<Document> convertAllCollectionsToCapped() {
        return databaseUtils.convertCollectionsToCapped(yamlConfig.getCappedSize());
    }

    private Flux<Void> dropAllCollections() {
        return Flux.fromIterable(MeasurementCollections.getMeasurementClasses())
                .flatMap(mongoTemplate::dropCollection);
    }
}
