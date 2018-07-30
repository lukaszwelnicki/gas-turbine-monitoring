package com.software.lukaszwelnicki.msc.generator;

import com.software.lukaszwelnicki.msc.measurements.Measurement;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@EqualsAndHashCode
public class DataGenerator<T extends Measurement> {

    private final T measurement;

    public List<T> generateRecordsInBetweenDates(LocalDateTime start, LocalDateTime end, int secondsBetweenReadings) {
        if (secondsBetweenReadings == 0) {
            throw new IllegalArgumentException("Seconds between readings must not be equal to zero");
        }
        List<T> records = new ArrayList<>();
        long numberOfRecords = Duration.between(start, end).getSeconds() / secondsBetweenReadings;
        for (int i = 0; i < numberOfRecords; i++) {
            @SuppressWarnings("unchecked")
            T record = (T) measurement.random();
            record.setCreatedDate(start.plusSeconds(secondsBetweenReadings * i));
            records.add(record);
        }
        return records;
    }

}
