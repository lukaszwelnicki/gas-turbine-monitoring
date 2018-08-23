package com.software.lukaszwelnicki.msc.generators;

import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
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
        long numberOfRecords = getNumberOfRecords(start, end, secondsBetweenReadings);
        List<T> records = new ArrayList<>();
        for (int i = 0; i < numberOfRecords; i++) {
            @SuppressWarnings("unchecked")
            T record = (T) measurement.random();
            record.setCreatedDate(start.plusSeconds(secondsBetweenReadings * i));
            records.add(record);
        }
        return records;
    }

    /*
        I had to use saveAll() on repository in conjunction
        with list creation in this metod to ensure
        that records will land in desired collections.
        Returning just one record did not work correctly.
    */
    @SuppressWarnings("unchecked")
    public List<T> generateRandomRecordAsList() {
        List<T> records = new ArrayList<>();
        T record = (T) measurement.random();
        records.add(record);
        return records;
    }

    final long getNumberOfRecords(LocalDateTime start, LocalDateTime end, int secondsBetweenReadings) {
        if (secondsBetweenReadings == 0) {
            throw new IllegalArgumentException("Seconds between readings must not be equal to zero");
        }
        if (end.isBefore(start) || end.isEqual(start)) {
            throw new IllegalArgumentException("End time should be after start time.");
        }
        return Duration.between(start, end).getSeconds() / secondsBetweenReadings;
    }


}
