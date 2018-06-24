package com.software.lukaszwelnicki.msc.generators;

import java.util.List;

public interface DataGenerator<T> {
    List<T> generateListOfRecords(int count);

}
