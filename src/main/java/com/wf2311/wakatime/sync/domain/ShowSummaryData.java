package com.wf2311.wakatime.sync.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.wf2311.wakatime.sync.entity.*;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 23:10.
 */
@Data
public class ShowSummaryData {
    private List<DayCategoryEntity> categories;
    private List<DayDependencyEntity> dependencies;
    private List<DayEditorEntity> editors;
    private List<DayEntityEntity> entities;
    @JSONField(name = "grand_total")
    private List<DayGrandTotalEntity> grandTotal;
    private List<DayLanguageEntity> languages;
    @JSONField(name = "operating_systems")
    private List<DayOperateSystemEntity> operatingSystems;
    private List<DayProjectEntity> projects;
}
