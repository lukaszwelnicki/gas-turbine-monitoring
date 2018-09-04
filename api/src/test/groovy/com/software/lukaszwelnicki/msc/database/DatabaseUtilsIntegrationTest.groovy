package com.software.lukaszwelnicki.msc.database


import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections
import com.software.lukaszwelnicki.msc.measurements.MeasurementRepositoryMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.FixedHostPortGenericContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import java.util.stream.Collectors

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
class DatabaseUtilsIntegrationTest extends Specification {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate

    @Autowired
    DatabaseUtils databaseUtils

    @Autowired
    MeasurementRepositoryMap repositories
    
    @Shared
    public GenericContainer mongo = new FixedHostPortGenericContainer('mongo:latest')
            .withFixedExposedPort(27019, 27017)

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
