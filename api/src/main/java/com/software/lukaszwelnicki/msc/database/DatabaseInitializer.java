package com.software.lukaszwelnicki.msc.database;

import com.software.lukaszwelnicki.msc.config.YAMLConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final DatabaseUtils databaseUtils;
    private final YAMLConfig yamlConfig;

    public void run(String... args) {
        databaseUtils.dropDatabase()
                .thenMany(databaseUtils.createCollections())
                .thenMany(databaseUtils.bootstrapDb
                        (yamlConfig.getMinutesSinceStartMonitoring(), yamlConfig.getSamplingSeconds()))
                .subscribe();
    }

}
