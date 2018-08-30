package com.software.lukaszwelnicki.msc.service

import com.software.lukaszwelnicki.msc.measurements.MeasurementRepositoryMap
import com.software.lukaszwelnicki.msc.measurements.documents.AftBMT
import com.software.lukaszwelnicki.msc.repositories.AftBMTRepository
import reactor.core.publisher.Flux
import spock.lang.Shared
import spock.lang.Specification

class MeasurementServiceTest extends Specification {

    @Shared
    def dummyMap = new HashMap()
    def repositories = Mock(MeasurementRepositoryMap)
    def aftBmtRepository = Mock(AftBMTRepository)
    def measurementService = new MeasurementService(repositories)

    def "setup"() {
        dummyMap.put("key", aftBmtRepository)
    }

    def "should return measurement repository"() {
        given:
            1 * repositories.getMap() >> dummyMap
            1 * aftBmtRepository.findBy() >> Flux.just(new AftBMT())
        expect:
            !measurementService.findMeasurementsByCollectionName("key").collectList().block().isEmpty()
    }

    def "should return tuple of repository name and repository class"() {
        given:
            def collectionName = AftBMT.COLLECTION_NAME
        expect:
            def tuple = measurementService.getCollectionNameAndClass(collectionName)
            tuple._1 == collectionName
            tuple._2 == AftBMT.class
    }

}
