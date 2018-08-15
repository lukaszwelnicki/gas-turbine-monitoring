package com.software.lukaszwelnicki.msc.measurements;

import com.software.lukaszwelnicki.msc.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeasurementRepositoryMap {

    private final AftBMTRepository aftBMTRepository;
    private final CompressorEfficiencyRepository compressorEfficiencyRepository;
    private final ForwardBMTRepository forwardBMTRepository;
    private final GeneratorVibrationsRepository generatorVibrationsRepository;
    private final TurbineEfficiencyRepository turbineEfficiencyRepository;
    private final TurbineVibrationsRepository turbineVibrationsRepository;

    private Map<String, MeasurementRepository<? extends Measurement>> collectionRepositoryMap;

    public Map<String, MeasurementRepository<? extends Measurement>> getMap() {
        return Optional.ofNullable(collectionRepositoryMap)
                .map(Collections::unmodifiableMap)
                .orElse(fillMap());
    }

    private Map<String, MeasurementRepository<? extends Measurement>> fillMap() {
        collectionRepositoryMap = new HashMap<>();
        collectionRepositoryMap.put(AftBMT.COLLECTION_NAME, aftBMTRepository);
        collectionRepositoryMap.put(CompressorEfficiency.COLLECTION_NAME, compressorEfficiencyRepository);
        collectionRepositoryMap.put(ForwardBMT.COLLECTION_NAME, forwardBMTRepository);
        collectionRepositoryMap.put(GeneratorVibrations.COLLECTION_NAME, generatorVibrationsRepository);
        collectionRepositoryMap.put(TurbineEfficiency.COLLECTION_NAME, turbineEfficiencyRepository);
        collectionRepositoryMap.put(TurbineVibrations.COLLECTION_NAME, turbineVibrationsRepository);
        return Collections.unmodifiableMap(collectionRepositoryMap);
    }

}
