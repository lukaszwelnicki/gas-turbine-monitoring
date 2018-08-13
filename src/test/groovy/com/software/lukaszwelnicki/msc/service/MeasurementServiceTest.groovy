package com.software.lukaszwelnicki.msc.service

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification

@RunWith(SpringRunner.class)
@DataMongoTest
class MeasurementServiceTest extends Specification {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate

    def "should return Flux of valid Measurement type"() {

    }


}
