package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.measurements.documents.ForwardBMT;
import reactor.core.publisher.Flux;

public interface ForwardBMTRepository extends MeasurementRepository<ForwardBMT> {
    @Override
    Flux<ForwardBMT> findBy();
}
