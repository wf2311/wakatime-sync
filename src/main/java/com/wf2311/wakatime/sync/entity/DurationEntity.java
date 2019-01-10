package com.wf2311.wakatime.sync.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:15.
 */
@Data
@Document(collection = "duration")
public class DurationEntity {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 依赖
     */
    private List<String> dependencies;

    /**
     * 持续时间（秒）
     */
    private Double duration;

    /**
     * IS DEBUGGING
     */
    private Boolean isDebugging;

    /**
     * 所属项目名称
     */
    private String project;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;


    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}