package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.model.Measurement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveMeasurementRepository<T extends Measurement> extends ReactiveMongoRepository<T,String> {

}
