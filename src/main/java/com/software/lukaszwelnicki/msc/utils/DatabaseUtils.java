package com.software.lukaszwelnicki.msc.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DatabaseUtils {

    private static final String DATABASE_NAME = "measurements";
    private static final String HOST = "localhost";
    private static final Integer MAX_RECORDS_IN_COLLECTION = 10000;

    private static final MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(HOST), DATABASE_NAME);
    private static final MongoDatabase database = mongoTemplate.getDb();

    private static void performOperationOnDB(Tuple2<Document, String> commandAndLogInfo) {
        log.info(commandAndLogInfo._2 + database.runCommand(commandAndLogInfo._1).toJson());
    }

    private static Tuple2<Document, String> createConvertToCappedCommandDocumentForCollectionName(String collectionName) {
        Map<String, Object> commands = new HashMap<>();
        commands.put("convertToCapped", collectionName);
        commands.put("size", MAX_RECORDS_IN_COLLECTION);
        return new Tuple2<>(new Document(commands), prepareLogMessageForCreateCappedCollection(collectionName));
    }

    private static String prepareLogMessageForCreateCappedCollection(String collectionName) {
        return "Convert " + collectionName + " to capped response: ";

    }

    public static void dropDatabase() {
        log.info("Drop database response: " +
                database.runCommand(new Document("dropDatabase", 1)).toJson());
    }

    public static void convertCollectionsToCapped() {
        MeasurementCollections.namesSet().stream()
                .map(DatabaseUtils::createConvertToCappedCommandDocumentForCollectionName)
                .forEach(DatabaseUtils::performOperationOnDB);
    }

}
