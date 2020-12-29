package com.wf2311.wakatime.sync.entity;

import com.wf2311.wakatime.sync.util.CommonUtil;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:15.
 */
@Data
@Entity
@Table(name = "project")
public class ProjectEntity {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    public void setName(String name) {
        this.name = CommonUtil.subStringIfOverLength(name, 200);
    }
}