package com.wf2311.wakatime.sync.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:04.
 */
@Data
public class Duration {
    private String dependencies;
    @JsonProperty("is_debugging")
    private Boolean isDebugging;
    private String project;
    @JsonProperty("time")
    private Double second;
    private Double duration;
}
