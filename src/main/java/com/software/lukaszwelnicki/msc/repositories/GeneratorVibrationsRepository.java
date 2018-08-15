package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.measurements.documents.GeneratorVibrations;
import reactor.core.publisher.Flux;

public interface GeneratorVibrationsRepository extends MeasurementRepository<GeneratorVibrations> {
    @Override
    Flux<GeneratorVibrations> findBy();
}
