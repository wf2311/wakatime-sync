package com.wf2311.wakatime.sync.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 23:10.
 */
@Data
public class SummaryDataVo {
    private List<DayTypeGroupSummaryUnit> editors;
    private List<DayTypeGroupSummaryUnit> languages;
    private List<DayTypeGroupSummaryUnit> categories;
    private List<DayTypeGroupSummaryUnit> operatingSystems;
    private List<DayProjectChartVo> projects;
}
