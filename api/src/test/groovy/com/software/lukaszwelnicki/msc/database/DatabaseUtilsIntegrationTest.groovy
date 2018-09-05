package com.software.lukaszwelnicki.msc.database

import com.software.lukaszwelnicki.msc.TestcontainersConfig
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections
import com.software.lukaszwelnicki.msc.measurements.MeasurementRepositoryMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import spock.lang.Shared

import java.util.stream.Collectors

class DatabaseUtilsIntegrationTest extends TestcontainersConfig {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate

    @Autowired
    DatabaseUtils databaseUtils

    @Autowired
    MeasurementRepositoryMap repositories

    @Shared
    static def collectionNamesRequired = Arrays.stream(MeasurementCollections.values())
            .map { v -> v.getCollectionName()}
            .collect(Collectors.toSet())

    def "should create collections for all measurement types"() {
        given:
            databaseUtils.createCollections().blockLast()
            def collectionNames = reactiveMongoTemplate.getCollectionNames().collectList().block()
        expect:
            collectionNamesRequired.stream()
                    .allMatch{s -> collectionNames.contains(s)}
            collectionNamesRequired.size() == collectionNames.size()
    }

    def "should bootstrap database"() {
        given:
            databaseUtils.bootstrapDb(1, 30).blockLast()
        expect:
            repositories.getMap().entrySet().stream()
                    .allMatch{ entry -> entry.getValue().findAll().count().block() == 2 }
    }

    def "should drop database"() {
        when:
            databaseUtils.dropDatabase().block()
        then:
            noExceptionThrown()
    }

}
