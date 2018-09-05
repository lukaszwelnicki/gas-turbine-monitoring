package com.software.lukaszwelnicki.msc.web

import com.software.lukaszwelnicki.msc.TestConfig
import com.software.lukaszwelnicki.msc.database.FillProcessStatus
import com.software.lukaszwelnicki.msc.measurements.documents.AftBMT
import com.software.lukaszwelnicki.msc.service.DatabaseFillProcessService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification

@WebFluxTest(DatabaseFillProcessRouter)
@Import(TestConfig)
class DatabaseFillProcessRouterTest extends Specification {

    @Autowired
    WebTestClient webTestClient

    @Autowired
    DatabaseFillProcessService dbService

    def status = FillProcessStatus.DISPOSED

    def "should start database fill process"() {
        given:
            1 * dbService.startDatabaseFillProcess() >> Flux.just(new AftBMT()).subscribe()
            1 * dbService.isProcessDisposed() >> Mono.just(status)
        expect:
            webTestClient.get()
                    .uri("/fill")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(status.class)
                    .isEqualTo(status)
    }

    def "should stop database fill process"() {
        given:
            1 * dbService.isProcessDisposed() >> Mono.just(status)
        expect:
            webTestClient.get()
                    .uri("/stopfill")
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON)
                    .expectBody(status.class)
                    .isEqualTo(status)
    }

}
