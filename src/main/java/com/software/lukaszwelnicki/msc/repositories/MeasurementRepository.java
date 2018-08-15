package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.measurements.Measurement;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface MeasurementRepository<T extends Measurement> extends ReactiveMongoRepository<T, String> {

    @Tailable
    @Query("{'_class': ?0}")
    Flux<T> findWithTailableCursorByClass(Class<? extends Measurement> clazz);

    @Tailable
    Flux<T> findBy();

}

