package com.software.lukaszwelnicki.msc.generators;

import java.util.List;

public interface DataGenerator<T> {
    List<T> generateListOfRandomMeasurements(int count);
    T generateRandomMeasurement();
}
