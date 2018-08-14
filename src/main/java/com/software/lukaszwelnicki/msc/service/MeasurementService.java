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

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository<? extends Measurement> measurementRepository;
    private final DatabaseFiller databaseFiller;

    private Disposable databaseFillProcess;

    public Flux<? extends Measurement> findMeasurementsByClass(Class<? extends Measurement> clazz) {
        return measurementRepository.findWithTailableCursorByClass(clazz);
    }

    public Flux<? extends Measurement> findMeasurementsByCollectionName(String name) {
        Class<? extends Measurement> measurementClass = MeasurementCollections.findMeasurementClassByCollectionName(name);
        return findMeasurementsByClass(measurementClass).share();
    }

    public Disposable startDatabaseFillProcess() {
        if (databaseFillProcess == null || databaseFillProcess.isDisposed()) {
            databaseFillProcess = databaseFiller.fillDatabase().subscribe(m -> log.info("Database fill process - inserted document id: " + m.getId()));
        }
        return databaseFillProcess;
    }

    public void killDatabaseFillProcess() {
        Optional.ofNullable(databaseFillProcess)
                .ifPresent(Disposable::dispose);
    }
}
