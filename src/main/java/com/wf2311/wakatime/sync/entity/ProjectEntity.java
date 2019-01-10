package com.wf2311.wakatime.sync.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:15.
 */
@Data
@Document(collection = "project")
public class ProjectEntity {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 项目隐私
     */
    private String privacy;

    /**
     * url
     */
    private String publicUrl;

    /**
     * 仓库
     */
    private String repository;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}