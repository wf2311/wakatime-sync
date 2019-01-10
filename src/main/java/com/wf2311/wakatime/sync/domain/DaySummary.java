package com.wf2311.wakatime.sync.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wf2311.wakatime.sync.domain.day.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:05.
 */
@Data
public class DaySummary {
    private List<DayCategory> categories;
    private List<DayDependency> dependencies;
    private List<DayEditor> editors;
    private List<DayEntity> entities;
    @JSONField(name = "grand_total")
    private DayGrandTotal grandTotal;
    private List<DayLanguage> languages;
    @JSONField(name = "operating_systems")
    private List<DayOperateSystem> system;
    private List<DayProject> projects;
    @JsonIgnore
    private LocalDate date;
}
