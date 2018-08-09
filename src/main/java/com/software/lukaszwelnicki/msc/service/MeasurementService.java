package com.software.lukaszwelnicki.msc.service;

import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MeasurementService {

    private final MeasurementRepository<? extends Measurement> measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository<? extends Measurement> measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Flux<? extends Measurement> findAllMeasurements() {
        return measurementRepository.findAllAftBmtWithTailableCursorBy();
    }
}
