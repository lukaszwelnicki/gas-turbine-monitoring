package com.software.lukaszwelnicki.msc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties
@EnableConfigurationProperties
public class YAMLConfig {

    private long cappedSize;
    private int samplingSeconds;
}
