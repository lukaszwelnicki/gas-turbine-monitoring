package com.software.lukaszwelnicki.msc.generator

import com.software.lukaszwelnicki.msc.measurements.AftBMT
import com.software.lukaszwelnicki.msc.measurements.Measurement
import com.software.lukaszwelnicki.msc.measurements.MeasurementCollections
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

@Unroll
class DataGeneratorTest extends Specification {

    @Shared
    Set<Class<? extends Measurement>> measurementsClasses = MeasurementCollections.getMeasurementClasses()

    @Shared
    Set<? extends Measurement> measurementsInstances = measurementsClasses.collect { it.newInstance() }

    @Shared
    Set<DataGenerator<? extends Measurement>> dataGenerators = measurementsInstances.collect { new DataGenerator<>(it) }

    @Shared
    DataGenerator<AftBMT> dataGenerator = new DataGenerator<>(new AftBMT())


    def "should return number of records = #result"() {
        given:
        LocalDateTime start = LocalDateTime.of(2018, 1, 1, 0, 0)
        LocalDateTime end = start.plusSeconds(seconds)

        when:
        long recordsCount = dataGenerator.getNumberOfRecords(start, end, secondsBetween)

        then:
        recordsCount == result

        where:
        seconds | secondsBetween || result
        15      | 15             || 1
        100     | 10             || 10
    }

    def "should throw IllegalArgumentException when secondsBetweenReadins == 0"() {
        when:
        dataGenerator.getNumberOfRecords(LocalDateTime.MIN, LocalDateTime.MAX, 0)

        then:
        IllegalArgumentException ex = thrown()
        ex.getMessage() == "Seconds between readings must not be equal to zero"
    }

    def "should throw IllegalArgumentException when end date is not after start date"() {
        when:
        dataGenerator.getNumberOfRecords(start, end, 5)

        then:
        IllegalArgumentException ex = thrown()
        ex.getMessage() == "End time should be after start time."

        where:
        start             | end
        LocalDateTime.MAX | LocalDateTime.MIN
        LocalDateTime.MAX | LocalDateTime.MAX
    }

    def "should return list of measurements of valid type"() {
        given:
        def end = LocalDateTime.now()
        def start = end.minusSeconds(100)
        List<? extends Measurement> resultsList =
                generator.generateRecordsInBetweenDates(start, end, 10)

        then:
        resultsList.get(0).getClass() == clazz

        where:
        generator << dataGenerators
        clazz << measurementsClasses

    }


}
