package com.wf2311.wakatime.sync.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:04.
 */
@Data
public class ProjectDuration {
    private String branch;
    private String dependencies;
    private String entity;
    private String language;
    private String type;
    @JSONField(name = "is_debugging")
    private Boolean isDebugging;
    private String project;
    @JSONField(name = "time")
    private Double second;
    private Double duration;
}
