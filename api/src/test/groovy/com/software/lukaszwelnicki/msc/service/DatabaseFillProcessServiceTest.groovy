package com.software.lukaszwelnicki.msc.service

import com.software.lukaszwelnicki.msc.database.DatabaseFiller
import com.software.lukaszwelnicki.msc.database.FillProcessStatus
import com.software.lukaszwelnicki.msc.measurements.documents.AftBMT
import reactor.core.publisher.Flux
import spock.lang.Shared
import spock.lang.Specification

import java.time.Duration

class DatabaseFillProcessServiceTest extends Specification {

    def databaseFiller = Mock(DatabaseFiller)
    def databaseFillProcessService = new DatabaseFillProcessService(databaseFiller)

    @Shared
    AftBMT dummyRecord = new AftBMT()

    def "setupSpec"() {
        dummyRecord.setId("id")
    }

    def "should start database fill process"() {
        given:
            1 * databaseFiller.fillDatabase() >>
                Flux.interval(Duration.ZERO, Duration.ofSeconds(1))
                        .map { l -> dummyRecord }
            databaseFillProcessService.startDatabaseFillProcess()
        expect:
            databaseFillProcessService.isProcessDisposed().block() == FillProcessStatus.NOT_DISPOSED
    }

    def "should stop database fill process"() {
        given:
            1 * databaseFiller.fillDatabase() >>
                Flux.interval(Duration.ZERO, Duration.ofSeconds(1))
                        .map { l -> dummyRecord }
        when:
            databaseFillProcessService.startDatabaseFillProcess()
            databaseFillProcessService.killDatabaseFillProcess()
        then:
            databaseFillProcessService.isProcessDisposed().block() == FillProcessStatus.DISPOSED
    }

}
