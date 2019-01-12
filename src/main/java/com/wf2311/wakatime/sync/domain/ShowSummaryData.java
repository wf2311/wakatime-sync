package com.wf2311.wakatime.sync.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 23:10.
 */
@Data
public class ShowSummaryData {
    private List<DayTypeGroupSummaryUnit> editors;
    private List<DayTypeGroupSummaryUnit> languages;
    @JSONField(name = "operating_systems")
    private List<DayTypeGroupSummaryUnit> operatingSystems;
    private List<DayProjectChartUnit> projects;
}
