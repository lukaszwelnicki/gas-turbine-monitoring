package com.software.lukaszwelnicki.msc.web;

import com.software.lukaszwelnicki.msc.database.FillProcessStatus;
import com.software.lukaszwelnicki.msc.service.DatabaseFillProcessService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class DatabaseFillProcessHandler implements WebFluxConfigurer {

    private final DatabaseFillProcessService fillProcessService;

    @NotNull
    Mono<ServerResponse> startFillingDatabase(ServerRequest serverRequest) {
        return Optional.ofNullable(fillProcessService.startDatabaseFillProcess())
                .map(fillProcess -> getServerResponseWithBody())
                .orElse(badRequest().build());
    }

    @NotNull
    Mono<ServerResponse> stopFillingDatabase(ServerRequest serverRequest) {
        fillProcessService.killDatabaseFillProcess();
        return getServerResponseWithBody();
    }

    private Mono<ServerResponse> getServerResponseWithBody() {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(fillProcessService.isProcessDisposed(), FillProcessStatus.class));
    }

}
