package com.software.lukaszwelnicki.msc.database

import com.software.lukaszwelnicki.msc.config.YAMLConfig
import com.software.lukaszwelnicki.msc.measurements.documents.AftBMT
import com.software.lukaszwelnicki.msc.measurements.documents.Measurement
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository
import reactor.core.publisher.Flux
import spock.lang.Specification

class DatabaseFillerTest extends Specification {

    def measurementRepository = Mock(MeasurementRepository)
    def yamlConfig = Mock(YAMLConfig)
    def databaseFiller = new DatabaseFiller(measurementRepository, yamlConfig)

    def "should fill database correctly"() {
        given:
            1 * yamlConfig.getSamplingSeconds() >> 1
            1 * measurementRepository.saveAll(_ as Iterable<? extends Measurement>) >> Flux.just(new AftBMT(1, 1, 1, 1));
        expect:
            databaseFiller.fillDatabase().blockFirst().getClass() == AftBMT.class
    }

}
