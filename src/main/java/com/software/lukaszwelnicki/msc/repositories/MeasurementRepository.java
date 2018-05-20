package com.software.lukaszwelnicki.msc.repositories;

import io.micrometer.core.instrument.Measurement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MeasurementRepository extends ReactiveMongoRepository<Measurement, String> {

}
