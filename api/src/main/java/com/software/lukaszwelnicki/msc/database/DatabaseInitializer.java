package com.software.lukaszwelnicki.msc.database;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.Success;
import com.software.lukaszwelnicki.msc.config.YAMLConfig;
import com.software.lukaszwelnicki.msc.generators.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
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

    private final DatabaseUtils databaseUtils;
    private final MeasurementRepository<Measurement> measurementRepository;
    private final ReactiveMongoTemplate mongoTemplate;
    private final YAMLConfig yamlConfig;

    @Value("${daysSinceStartMonitoring}")
    private long daysSinceStartMonitoring;

    public void run(String... args) {
        dropDatabase()
                .thenMany(createCollections())
                .thenMany(bootstrapDb(daysSinceStartMonitoring, yamlConfig.getSamplingSeconds()))
                .subscribe();
    }

    Flux<MongoCollection<Document>> createCollections() {
        return Flux.fromIterable(MeasurementCollections.getMeasurementClasses())
                .flatMap(databaseUtils::createCappedCollectionByClass);
    }

    Mono<Success> dropDatabase() {
        return Mono.from(mongoTemplate.getMongoDatabase().drop());
    }

    Flux<? extends Measurement> bootstrapDb(long daysSinceStartMonitoring, int samplingSeconds) {
        return Flux.fromIterable(DataGeneratorsSet.INSTANCE.getDataGenerators())
                .flatMap(g -> measurementRepository
                        .saveAll(g.generateRecordsInBetweenDates
                                (LocalDateTime.now().minusDays(daysSinceStartMonitoring),
                                        LocalDateTime.now(), samplingSeconds)));
    }


}
