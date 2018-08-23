package com.software.lukaszwelnicki.msc.database;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.software.lukaszwelnicki.msc.config.YAMLConfig;
import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
class DatabaseUtils {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final YAMLConfig yamlConfig;

    Mono<Document> convertCollectionToCapped(String collectionName, long maxRecords) {
        final MongoDatabase db = reactiveMongoTemplate.getMongoDatabase();
        return Mono.from(Optional.of(collectionName)
                .map(s -> prepareMongoCommandForCapped(s, maxRecords))
                .map(db::runCommand)
                .orElse(null));
    }

    Mono<MongoCollection<Document>> createCappedCollectionByClass(Class<? extends Measurement> clazz) {
        return reactiveMongoTemplate.createCollection(clazz, prepareCollectionOptionsForCapped());
    }

    private CollectionOptions prepareCollectionOptionsForCapped() {
        return CollectionOptions.empty()
                .maxDocuments(yamlConfig.getCappedCount())
                .size(yamlConfig.getCappedSize())
                .capped();
    }

    private Document prepareMongoCommandForCapped(String collectionName, long maxRecords) {
        Map<String, Object> commands = new HashMap<>();
        commands.put("convertToCapped", collectionName);
        commands.put("size", maxRecords);
        return new Document(commands);
    }

}
