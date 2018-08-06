package com.software.lukaszwelnicki.msc.database;

import com.mongodb.client.MongoDatabase;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
class DatabaseUtils {

    static void dropMongoDatabase(MongoDatabase database) {
        log.info("Drop database response: " +
                database.runCommand(new Document("dropDatabase", 1)).toJson());
    }

    static void convertToCapped(MongoDatabase db, Collection<String> collectionsNames, int maxRecords) {
        collectionsNames.stream()
                .map(s -> createConvertToCappedCommand(s, maxRecords))
                .forEach(t -> performOperationOnDB(t, db));
    }

    private static void performOperationOnDB(Tuple2<Document, String> commandAndLogInfo, MongoDatabase db) {
        log.info(commandAndLogInfo._2 + db.runCommand(commandAndLogInfo._1).toJson());
    }

    private static Tuple2<Document, String> createConvertToCappedCommand(String collectionName, int maxRecords) {
        Map<String, Object> commands = new HashMap<>();
        commands.put("convertToCapped", collectionName);
        commands.put("size", maxRecords);
        return new Tuple2<>(new Document(commands), prepareLogMessageForCreateCappedCollection(collectionName));
    }

    private static String prepareLogMessageForCreateCappedCollection(String collectionName) {
        return "Convert " + collectionName + " to capped response: ";

    }

}
