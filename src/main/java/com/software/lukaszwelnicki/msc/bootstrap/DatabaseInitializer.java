package com.software.lukaszwelnicki.msc.bootstrap;

import com.software.lukaszwelnicki.msc.model.*;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final MeasurementRepository measurementRepository;

    @Override
    public void run(String... args) throws Exception {
        if(measurementRepository.count().block().equals(0)) {
            bootstrapDB(1000);
        }
    }

    private void bootstrapDB(int numberOfRecords) {
        for(int i = 0; i < numberOfRecords; i++) {
            measurementRepository.save(AftBMT.random());
            measurementRepository.save(CompressorEfficiency.random());
            measurementRepository.save(ForwardBMT.random());
            measurementRepository.save(GeneratorVibrations.random());
            measurementRepository.save(TurbineEfficiency.random());
            measurementRepository.save(TurbineVibrations.random());
        }
    }

    @Autowired
    public DatabaseInitializer(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }
}
