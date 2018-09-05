package com.software.lukaszwelnicki.msc.web

import com.software.lukaszwelnicki.msc.TestConfig
import com.software.lukaszwelnicki.msc.measurements.documents.AftBMT
import com.software.lukaszwelnicki.msc.measurements.documents.Measurement
import com.software.lukaszwelnicki.msc.service.MeasurementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.FluxExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.Duration

@WebFluxTest(MeasurementRouter)
@Import(TestConfig)
class MeasurementRouterTest extends Specification {

    @Autowired
    WebTestClient webTestClient

    @Autowired
    MeasurementService measurementService

    def dummyReading = new AftBMT()

    def "should get stream of measurements"() {
        given:
            1 * measurementService.getCollectionNameAndClass(_) >> new Tuple2<String, ? extends Class<? extends Measurement>>("aft_bmt", AftBMT.class)
            1 * measurementService.findMeasurementsByCollectionName(_) >> Flux.interval(Duration.ZERO, Duration.ofSeconds(1)).map { l -> dummyReading }
        expect:
            FluxExchangeResult<AftBMT> result = webTestClient.get()
                    .uri("/measurements/aft_bmt")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .exchange()
                    .expectStatus().isOk()
                    .returnResult(AftBMT.class)
            StepVerifier.create(result.getResponseBody())
                    .expectNext(dummyReading)
                    .thenCancel()
                    .verify()
    }
}
