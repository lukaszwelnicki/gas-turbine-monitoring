package com.software.lukaszwelnicki.msc.database;

import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
class DatabaseUtils {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    Flux<Document> convertCollectionsToCapped(long maxRecords) {
        final MongoDatabase db = reactiveMongoTemplate.getMongoDatabase();
        return reactiveMongoTemplate.getCollectionNames()
                .map(s -> convertCollectionToCappedDocument(s, maxRecords))
                .flatMap(db::runCommand);
    }

    private Document convertCollectionToCappedDocument(String collectionName, long maxRecords) {
        Map<String, Object> commands = new HashMap<>();
        commands.put("convertToCapped", collectionName);
        commands.put("size", maxRecords);
        return new Document(commands);
    }

}
