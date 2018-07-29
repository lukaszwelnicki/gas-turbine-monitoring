package com.software.lukaszwelnicki.msc.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

@Slf4j
public class DatabaseUtils {

    private static final String DATABASE_NAME = "measurements";
    private static final String HOST = "localhost";
    private static final int MAX_RECORDS_IN_COLLECTION = 10000;

    private static final MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(HOST), DATABASE_NAME);
    private static final MongoDatabase database = mongoTemplate.getDb();

    private static void performOperationOnDB(Document operation) {
        Document result = database.runCommand(operation);
        log.info(result.toJson());
    }

    private static Document createConvertToCappedCommandDocumentForCollectionName(String name) {
        Map<String, Object> commands = Map.of("convertToCapped", name, "size", MAX_RECORDS_IN_COLLECTION);
        return new Document(commands);
    }

    public static void dropDatabase() {
        log.info(database.runCommand(new Document("dropDatabase", 1)).toJson());
    }

    public static void convertCollectionsToCapped() {
        MeasurementCollections.namesSet().stream()
                .map(DatabaseUtils::createConvertToCappedCommandDocumentForCollectionName)
                .forEach(DatabaseUtils::performOperationOnDB);
    }

}
