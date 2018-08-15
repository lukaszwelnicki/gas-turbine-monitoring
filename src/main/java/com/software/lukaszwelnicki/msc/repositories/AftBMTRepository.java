package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.measurements.documents.AftBMT;
import reactor.core.publisher.Flux;

public interface AftBMTRepository extends MeasurementRepository<AftBMT> {
    @Override
    Flux<AftBMT> findBy();
}
