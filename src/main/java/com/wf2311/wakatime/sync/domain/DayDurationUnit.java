package com.wf2311.wakatime.sync.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 23:45.
 */
@Data
public class DayDurationUnit {
    private Integer year;
    @JsonIgnore
    private Integer month;
    @JsonIgnore
    private Integer day;
    private LocalDate date;
    private Double total;

    public LocalDate getDate() {
        return LocalDate.of(year, month, day);
    }
}
