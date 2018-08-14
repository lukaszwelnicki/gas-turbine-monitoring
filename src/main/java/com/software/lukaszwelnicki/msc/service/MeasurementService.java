package com.software.lukaszwelnicki.msc.service;

import com.software.lukaszwelnicki.msc.database.DatabaseFiller;
import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository<? extends Measurement> measurementRepository;
    private final DatabaseFiller databaseFiller;

    private Disposable databaseFillProcess;

    public Flux<? extends Measurement> findMeasurementsByClass(Class<? extends Measurement> clazz) {
        return measurementRepository.findAllByClass(clazz);
    }

    public Flux<? extends Measurement> findMeasurementsByCollectionName(String name) {
        Class<? extends Measurement> measurementClass = MeasurementCollections.findMeasurementClassByCollectionName(name);
        return findMeasurementsByClass(measurementClass);
    }

    public void startDatabaseFillProcess() {
        databaseFillProcess = databaseFiller.fillDatabase().subscribe(m -> System.out.println(m.getCreatedDate()));
    }

    public void killDatabaseFillProcess() {
        databaseFillProcess.dispose();
    }
}
