package com.software.lukaszwelnicki.msc.generator;

import com.software.lukaszwelnicki.msc.model.Measurement;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator<T extends Measurement> {

    private T measurement;

    public List<T> generateRecordsInBetweenDates(LocalDateTime start, LocalDateTime end, int secondsBetweenReadings) {
        List<T> records = new ArrayList<>();
        long numberOfRecords = Duration.between(start, end).getSeconds() / secondsBetweenReadings;
        for (int i = 0; i < numberOfRecords; i++) {
            T record = (T) measurement.random();
            record.setCreatedDate(start.plusSeconds(secondsBetweenReadings * i));
            records.add(record);
        }
        return records;
    }

    public DataGenerator(T measurement) {
        this.measurement = measurement;
    }

}
