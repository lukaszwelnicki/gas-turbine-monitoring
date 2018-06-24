package com.software.lukaszwelnicki.msc.generator;

import java.time.LocalDateTime;
import java.util.List;

public interface DataGenerator<T> {
    List<T> generateRecordsInBetweenDates(LocalDateTime start, LocalDateTime end, int secondsBetweenReadings);
}
