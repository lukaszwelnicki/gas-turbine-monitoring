package com.software.lukaszwelnicki.msc.generator;

import com.software.lukaszwelnicki.msc.model.AftBMT;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class AftBMTDataGenerator implements DataGenerator<AftBMT> {

    @Override
    public List<AftBMT> generateRecordsInBetweenDates(LocalDateTime start, LocalDateTime end, int secondsBetweenReadings) {
        long numberOfRecords = Duration.between(start, end).getSeconds() / secondsBetweenReadings;

    }
}
