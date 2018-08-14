package com.software.lukaszwelnicki.msc.web;

import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class MeasurementHandler {

    private final MeasurementService measurementService;

    public Mono<ServerResponse> allByCollectionName(ServerRequest request) {
        return Optional.of(request.pathVariable("collection"))
                .map(MeasurementCollections::findMeasurementClassByCollectionName)
                .map(this::getServerResponseAndData)
                .orElse(notFound().build());
    }

    private Mono<ServerResponse> getServerResponseAndData(Class<? extends Measurement> clazz) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(measurementService.findMeasurementsByClass(clazz), ParameterizedTypeReference.forType(Measurement.class))
                .switchIfEmpty(notFound().build());
    }

}
