package com.software.lukaszwelnicki.msc.repositories;

import com.software.lukaszwelnicki.msc.model.CompressorEfficiency;
import com.software.lukaszwelnicki.msc.model.Measurement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CompressorEfficiencyRepository<T extends CompressorEfficiency> extends MeasurementRepository<T> {

}
