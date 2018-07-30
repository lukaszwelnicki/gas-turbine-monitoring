package com.software.lukaszwelnicki.msc.bootstrap;

import com.software.lukaszwelnicki.msc.generator.DataGenerator;
import com.software.lukaszwelnicki.msc.generator.DataGeneratorsSet;
import com.software.lukaszwelnicki.msc.measurements.Measurement;
import com.software.lukaszwelnicki.msc.repositories.MeasurementRepository;
import com.software.lukaszwelnicki.msc.utils.DatabaseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private static final int SECONDS_BETWEEN_RECORDS = 15;
    private static final LocalDateTime START = LocalDateTime.now().minusDays(3);
    private static final LocalDateTime END = LocalDateTime.now();

    private final MeasurementRepository measurementRepository;

    public void run(String... args) {
        DatabaseUtils.dropDatabase();
        bootstrapDB();
        DatabaseUtils.convertCollectionsToCapped();
    }

    private void bootstrapDB() {
        DataGeneratorsSet.getDataGenerators().forEach(g ->
                measurementRepository.saveAll(generateEntities(g)).subscribe());
    }

    private List<Measurement> generateEntities(DataGenerator<Measurement> g) {
        return g.generateRecordsInBetweenDates(START, END, SECONDS_BETWEEN_RECORDS);
    }
}
