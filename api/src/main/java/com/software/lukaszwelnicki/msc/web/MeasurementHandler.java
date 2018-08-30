package com.software.lukaszwelnicki.msc.web;

import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import com.software.lukaszwelnicki.msc.service.MeasurementService;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class MeasurementHandler implements WebFluxConfigurer {

    private final MeasurementService measurementService;

    Mono<ServerResponse> allByCollectionName(ServerRequest request) {
        return Optional.of(request.pathVariable("collection"))
                .map(measurementService::getCollectionNameAndClass)
                .map(this::getServerResponseAndData)
                .orElse(notFound().build());
    }

    private Mono<ServerResponse> getServerResponseAndData(Tuple2<String, ? extends Class<? extends Measurement>> collectionNameAndClass) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(measurementService.findMeasurementsByCollectionName(collectionNameAndClass._1),
                        ParameterizedTypeReference.forType(collectionNameAndClass._2))
                .switchIfEmpty(notFound().build());
    }

}
