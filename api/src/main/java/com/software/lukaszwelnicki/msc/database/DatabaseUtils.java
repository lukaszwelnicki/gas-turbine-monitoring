package com.software.lukaszwelnicki.msc.database;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.Success;
import com.software.lukaszwelnicki.msc.config.YAMLConfig;
import com.software.lukaszwelnicki.msc.generators.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
class DatabaseUtils {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final YAMLConfig yamlConfig;
    private final MeasurementRepository<Measurement> measurementRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    Flux<MongoCollection<Document>> createCollections() {
        return Flux.fromIterable(MeasurementCollections.getMeasurementClasses())
                .flatMap(this::createCappedCollectionByClass);
    }

    Mono<Success> dropDatabase() {
        return Mono.from(mongoTemplate.getMongoDatabase().drop());
    }

    Flux<? extends Measurement> bootstrapDb(long minutesAgo, int samplingSeconds) {
        LocalDateTime now = LocalDateTime.now();
        return Flux.fromIterable(DataGeneratorsSet.INSTANCE.getDataGenerators())
                .flatMap(g -> measurementRepository
                        .saveAll(g.generateRecordsInBetweenDates
                                (now.minusMinutes(minutesAgo), now, samplingSeconds)));
    }

    Mono<MongoCollection<Document>> createCappedCollectionByClass(Class<? extends Measurement> clazz) {
        return reactiveMongoTemplate.createCollection(clazz, prepareCollectionOptionsForCapped());
    }

    CollectionOptions prepareCollectionOptionsForCapped() {
        return CollectionOptions.empty()
                .maxDocuments(yamlConfig.getCappedCount())
                .size(yamlConfig.getCappedSize())
                .capped();
    }

}
