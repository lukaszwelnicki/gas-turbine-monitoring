package com.software.lukaszwelnicki.msc.service;

import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.measurements.MeasurementRepositoryMap;
import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepositoryMap repositories;

    public Tuple2<String, ? extends Class<? extends Measurement>> getCollectionNameAndClass(String s) {
        return new Tuple2<>(s, MeasurementCollections.findMeasurementClassByCollectionName(s));
    }

    public Flux<? extends Measurement> findMeasurementsByCollectionName(String name) {
        return getRepository(name).findBy();
    }

    private MeasurementRepository<? extends Measurement> getRepository(String name) {
        return repositories.getMap().get(name);
    }
}
