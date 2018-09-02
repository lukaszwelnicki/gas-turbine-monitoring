package com.software.lukaszwelnicki.msc.database;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.software.lukaszwelnicki.msc.config.YAMLConfig;
import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
class DatabaseUtils {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final YAMLConfig yamlConfig;

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
