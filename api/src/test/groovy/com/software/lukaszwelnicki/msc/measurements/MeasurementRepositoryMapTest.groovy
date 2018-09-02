package com.software.lukaszwelnicki.msc.measurements

import com.software.lukaszwelnicki.msc.repositories.*
import spock.lang.Specification

class MeasurementRepositoryMapTest extends Specification {

    def aftBMTRepository = Mock(AftBMTRepository);
    def compressorEfficiencyRepository = Mock(CompressorEfficiencyRepository);
    def forwardBMTRepository = Mock(ForwardBMTRepository);
    def generatorVibrationsRepository = Mock(GeneratorVibrationsRepository);
    def turbineEfficiencyRepository = Mock(TurbineEfficiencyRepository);
    def turbineVibrationsRepository = Mock(TurbineVibrationsRepository);

    def measurementRepositoryMap = new MeasurementRepositoryMap(
            aftBMTRepository,
            compressorEfficiencyRepository,
            forwardBMTRepository,
            generatorVibrationsRepository,
            turbineEfficiencyRepository,
            turbineVibrationsRepository)

    def "should return map containing all repositories"() {
        expect:
            measurementRepositoryMap.getMap().get("aft_bmt") == aftBMTRepository
            measurementRepositoryMap.getMap().size() == 6
    }
}
