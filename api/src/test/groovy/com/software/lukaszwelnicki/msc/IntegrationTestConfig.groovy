package com.software.lukaszwelnicki.msc

import com.software.lukaszwelnicki.msc.service.DatabaseFillProcessService
import com.software.lukaszwelnicki.msc.service.MeasurementService
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

@TestConfiguration
class IntegrationTestConfig {

    def mockFactory = new DetachedMockFactory()

    @Bean
    DatabaseFillProcessService databaseFillProcessService() {
        return mockFactory.Mock(DatabaseFillProcessService)
    }

    @Bean
    MeasurementService measurementService() {
        return mockFactory.Mock(MeasurementService)
    }
}
