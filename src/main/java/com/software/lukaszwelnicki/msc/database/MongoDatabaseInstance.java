package com.software.lukaszwelnicki.msc.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

public enum MongoDatabaseInstance {
    INSTANCE;

    private MongoDatabase database;

    public MongoDatabase getDatabase(String host, String databaseName) {
        return Optional.ofNullable(database)
                .orElseGet(() -> prepareDatabase(host, databaseName));
    }

    private MongoDatabase prepareDatabase(String host, String databaseName) {
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(host), databaseName);
        database = mongoTemplate.getDb();
        return database;
    }
}
