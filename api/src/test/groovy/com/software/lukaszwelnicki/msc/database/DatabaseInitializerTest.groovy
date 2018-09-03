package com.software.lukaszwelnicki.msc.database

import com.mongodb.Mongo
import com.mongodb.MongoClient
import com.mongodb.reactivestreams.client.Success
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.IMongodConfig
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.util.stream.Collectors

@SpringBootTest
@EnableAutoConfiguration(exclude=[MongoAutoConfiguration.class, MongoDataAutoConfiguration.class])
class DatabaseInitializerTest extends Specification {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate

    @Autowired
    DatabaseUtils databaseUtils

    @Autowired
    DatabaseInitializer databaseInitializer

    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "dbtest";
    private static final int MONGO_TEST_PORT = 27028;
    private static MongodProcess mongoProcess;
    private static Mongo mongo;

    def collectionNamesRequired = Arrays.stream(MeasurementCollections.values())
            .map { v -> v.getCollectionName()}
            .collect(Collectors.toSet())

    def "setupSpec"() {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.LATEST_NIGHTLY)
                .net(new Net(LOCALHOST, MONGO_TEST_PORT, Network.localhostIsIPv6()))
                .build();
        MongodExecutable mongodExecutable = null;
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            mongoProcess = mongodExecutable.start();
            mongo = new MongoClient(LOCALHOST, MONGO_TEST_PORT);
            mongo.getDB(DB_NAME);
        } finally {
            if (mongodExecutable != null)
                mongodExecutable.stop();
        }
    }

    def "should create collections for all measurement types"() {
        given:
            databaseInitializer.createCollections()
            def collectionNames = reactiveMongoTemplate.getCollectionNames().collectList().block()
        expect:
            collectionNames.stream()
                    .allMatch{s -> collectionNamesRequired.contains(s)}
    }

    def "should bootstrap database"() {
        given:
            databaseInitializer.bootstrapDb(1, 43200).blockLast()
        expect:
            collectionNamesRequired.stream()
                    .map{name -> reactiveMongoTemplate.getCollection(name)}
                    .allMatch{collection -> Mono.from(collection.count()).block() == 2}
    }

    def "should drop database"() {
        expect:
            databaseInitializer.dropDatabase().block().getClass() == Success
    }

    def "cleanupSpec"() {
        if (mongo != null) mongo.close();
        if (mongoProcess != null) mongoProcess.stop();
    }

}
