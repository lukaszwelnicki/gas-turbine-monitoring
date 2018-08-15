package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.measurements.CompressorEfficiency;
import reactor.core.publisher.Flux;

public interface CompressorEfficiencyRepository extends MeasurementRepository<CompressorEfficiency> {
    @Override
    Flux<CompressorEfficiency> findBy();
}
