package com.wf2311.wakatime.sync.domain.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-17 22:14.
 */
@Data
public class SimpleDayDurationVo {
    private LocalDate day;
    private Integer totalSeconds;
    private List<DayTypeGroupSummaryUnit> projects;
    private List<DayTypeGroupSummaryUnit> languages;
    private List<DayTypeGroupSummaryUnit> editors;
    private List<DayTypeGroupSummaryUnit> actions;
    private List<DayTypeGroupSummaryUnit> systems;
}
