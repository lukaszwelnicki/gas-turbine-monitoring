package com.software.lukaszwelnicki.msc.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class DatabaseFillProcessRouter {

    @Bean
    public RouterFunction<ServerResponse> routeFillProcess(DatabaseFillProcessHandler databaseFillProcessHandler) {
        return RouterFunctions
                .route(GET("/fill")
                        .and(accept(MediaType.APPLICATION_JSON)), databaseFillProcessHandler::startFillingDatabase)
                .andRoute(GET("/stopfill")
                        .and(accept(MediaType.APPLICATION_JSON)), databaseFillProcessHandler::stopFillingDatabase);
    }

}
