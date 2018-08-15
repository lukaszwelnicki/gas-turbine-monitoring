package com.software.lukaszwelnicki.msc.measurements;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
abstract public class Measurement {

    @Id
    private String id;

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    public abstract Measurement random();

    public Measurement() {
        this.createdDate = LocalDateTime.now();
    }
}
