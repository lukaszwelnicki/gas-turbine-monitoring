package com.software.lukaszwelnicki.msc.controller;

import com.software.lukaszwelnicki.msc.measurements.AftBMT;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import com.software.lukaszwelnicki.msc.service.MeasurementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class MeasurementsController {

    private MeasurementService measurementService;
    private MeasurementRepository<AftBMT> repo;

    public MeasurementsController(MeasurementService measurementService, MeasurementRepository<AftBMT> repo) {
        this.measurementService = measurementService;
        this.repo = repo;
    }

    @GetMapping(value = "/AftBmt")
    @ResponseBody
    public Flux<AftBMT> getAftBmtMeasurements() {
        return repo.findAllAftBmtWithTailableCursorBy().delayElements(Duration.ofSeconds(2));
    }

    @GetMapping(value = "/findAll")
    @ResponseBody
    public Flux<String> findAll() {
        return Flux.just("jeden", "dwa", "trzy", "cztery");
    }
}
