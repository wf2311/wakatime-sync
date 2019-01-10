package com.wf2311.wakatime.sync.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:04.
 */
@Data
public class HeartBeat {
    private String entity;
    @JSONField(name = "id")
    private String uuid;
    @JSONField(name = "time")
    private Double timestamp;
    private String type;
    @JsonIgnore
    private String simpleName;
}
