package com.software.lukaszwelnicki.msc.bootstrap;

import com.software.lukaszwelnicki.msc.generator.DataGenerator;
import com.software.lukaszwelnicki.msc.model.*;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final MeasurementRepository measurementRepository;

    @Override
    public void run(String... args) throws Exception {
        measurementRepository.deleteAll();
        bootstrapDB(LocalDateTime.now().minusMonths(3), LocalDateTime.now(), 15);
        log.info("Finished bootstrap. Number of recorts: " + measurementRepository.count().block());
    }

    private void bootstrapDB(LocalDateTime start, LocalDateTime end, int secondsBetweenRecords) {
        List<DataGenerator> generators = new ArrayList<>();
        generators.add(new DataGenerator<>(new AftBMT()));
        generators.add(new DataGenerator<>(new CompressorEfficiency()));
        generators.add(new DataGenerator<>(new ForwardBMT()));
        generators.add(new DataGenerator<>(new GeneratorVibrations()));
        generators.add(new DataGenerator<>(new TurbineEfficiency()));
        generators.add(new DataGenerator<>(new TurbineVibrations()));
        generators.forEach(g -> measurementRepository.saveAll(g.generateRecordsInBetweenDates(start, end, secondsBetweenRecords)));
    }


    @Autowired
    public DatabaseInitializer(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }
}
