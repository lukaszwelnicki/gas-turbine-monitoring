package com.software.lukaszwelnicki.msc.generators;

import com.software.lukaszwelnicki.msc.measurements.documents.Measurement;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@EqualsAndHashCode
public class DataGenerator<T extends Measurement> {

    private final T measurement;

    @SuppressWarnings("unchecked")
    public List<T> generateRecordsInBetweenDates(LocalDateTime start, LocalDateTime end, int secondsBetweenReadings) {
        final BigInteger sampling = BigInteger.valueOf(secondsBetweenReadings);
        int numberOfRecords = getNumberOfRecords(start, end, secondsBetweenReadings);
        List<T> records = new ArrayList<>(numberOfRecords);
        IntStream.range(0, numberOfRecords)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .forEach(i -> {
                    T record = (T) measurement.random();
                    record.setCreatedDate(start.plusSeconds(i.multiply(sampling).longValue()));
                    records.add(i.intValue(), record);
                });
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

    final int getNumberOfRecords(LocalDateTime start, LocalDateTime end, int secondsBetweenReadings) {
        if (secondsBetweenReadings == 0) {
            throw new IllegalArgumentException("Seconds between readings must not be equal to zero");
        }
        if (end.isBefore(start) || end.isEqual(start)) {
            throw new IllegalArgumentException("End time should be after start time.");
        }
        return (int) (Duration.between(start, end).getSeconds() / secondsBetweenReadings);
    }


}
