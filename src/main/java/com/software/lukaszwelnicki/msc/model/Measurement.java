package com.software.lukaszwelnicki.msc.model;

import lombok.NoArgConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgConstructor
abstract public class Measurement {

    @Id
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdDate;

}
