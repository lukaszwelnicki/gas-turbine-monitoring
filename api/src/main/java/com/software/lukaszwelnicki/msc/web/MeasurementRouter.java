package com.software.lukaszwelnicki.msc.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class MeasurementRouter {

    @Bean
    public RouterFunction<ServerResponse> routeMeasurements(MeasurementHandler measurementHandler) {
        return RouterFunctions.route(GET("/measurements/{collection}")
                .and(accept(TEXT_EVENT_STREAM)), measurementHandler::allByCollectionName);
    }

    @Bean
    public RouterFunction<ServerResponse> routeFillProcess(FillProcessHandler fillProcessHandler) {
        return RouterFunctions.route(GET("/fill"), fillProcessHandler::startFillingDatabase)
                .andRoute(GET("/stopfill"), fillProcessHandler::stopFillingDatabase);
    }


}
