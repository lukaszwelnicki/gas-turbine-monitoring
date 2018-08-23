package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MeasurementRepository<T extends Measurement> extends ReactiveMongoRepository<T, String> {

    @Tailable
    Flux<T> findBy();

}

