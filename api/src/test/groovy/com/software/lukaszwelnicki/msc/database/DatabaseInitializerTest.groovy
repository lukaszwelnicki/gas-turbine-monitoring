package com.software.lukaszwelnicki.msc.database

import com.mongodb.reactivestreams.client.Success
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.util.stream.Collectors

@SpringBootTest
class DatabaseInitializerTest extends Specification {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate

    @Autowired
    DatabaseUtils databaseUtils

    @Autowired
    DatabaseInitializer databaseInitializer

    def collectionNamesRequired = Arrays.stream(MeasurementCollections.values())
            .map { v -> v.getCollectionName()}
            .collect(Collectors.toSet())

    def "should create collections for all measurement types"() {
        given:
            databaseInitializer.createCollections()
            def collectionNames = reactiveMongoTemplate.getCollectionNames().collectList().block()
        expect:
            collectionNames.stream()
                    .allMatch{s -> collectionNamesRequired.contains(s)}
    }

    def "should drop database"() {
        expect:
            databaseInitializer.dropDatabase().block().getClass() == Success
    }

    def "should bootstrap database"() {
        given:
            databaseInitializer.bootstrapDb(1, 43200).blockLast()
        expect:
            collectionNamesRequired.stream()
                    .map{name -> reactiveMongoTemplate.getCollection(name)}
                    .allMatch{collection -> Mono.from(collection.count()).block() == 2}
    }
}
