package com.wf2311.wakatime.sync.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:05.
 */
@Data
public class Project {
    @JsonProperty( "id")
    private String uuid;
    private String name;
    @JsonProperty( "public_url")
    private String publicUrl;
    private String repository;
    private String privacy;
}
