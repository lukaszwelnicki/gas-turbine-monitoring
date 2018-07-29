package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.measurements.Measurement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MeasurementRepository<T extends Measurement> extends ReactiveMongoRepository<T, String> {
}

