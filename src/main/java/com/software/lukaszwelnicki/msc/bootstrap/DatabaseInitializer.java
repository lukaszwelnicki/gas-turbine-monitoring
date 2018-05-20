package com.software.lukaszwelnicki.msc.bootstrap;

import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final MeasurementRepository measurementRepository;

    @Override
    public void run(String... args) throws Exception {
        if(measurementRepository.count().block() == 0){

        }
    }

    @Autowired
    public DatabaseInitializer(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }
}
