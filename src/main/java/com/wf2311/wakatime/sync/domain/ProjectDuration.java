package com.wf2311.wakatime.sync.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wf2311.wakatime.sync.util.StringArrayConverter;
import lombok.Data;

import javax.persistence.Convert;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:04.
 */
@Data
public class ProjectDuration {
    private String branch;
    @Convert(converter = StringArrayConverter.class)
    private List<String> dependencies;
    private String entity;
    private String language;
    private String type;
    @JsonProperty("is_debugging")
    private Boolean isDebugging;
    private String project;
    @JsonProperty("time")
    private Double second;
    private Double duration;
}
