package com.wf2311.wakatime.sync.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wf2311.wakatime.sync.domain.day.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2020/9/25 16:50.
 */
@Data
public class DumpDaySummary {
    private List<DayCategory> categories;
    private List<DayDependency> dependencies;
    private List<DayEditor> editors;
    @JsonProperty("grand_total")
    private DayGrandTotal grandTotal;
    private List<DayLanguage> languages;
    private List<DayMachine> machines;
    @JsonProperty("operating_systems")
    private List<DayOperateSystem> system;
    @JsonIgnore
    private LocalDate date;
}
