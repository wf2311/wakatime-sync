package com.wf2311.wakatime.sync.entity;

import com.wf2311.wakatime.sync.util.CommonUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
public class ProjectEntity extends PanacheEntityBase {
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
    @Column(name = "name", length = 200)
    private String name;

    /**
     * uuid
     */
    @Column(name = "uuid", length = 64)
    private String uuid;

    /**
     * 项目隐私
     */
    private String privacy;

    /**
     * url
     */
    @Column(name = "url", length = 200)
    private String publicUrl;

    /**
     * 仓库
     */
    @Column(name = "repository", length = 200)
    private String repository;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;


    public void setName(String name) {
        this.name = CommonUtil.subStringIfOverLength(name, 200);
    }

    public void setUuid(String uuid) {
        this.uuid = CommonUtil.subStringIfOverLength(uuid, 64);
    }

    public void setPrivacy(String privacy) {
        this.privacy = CommonUtil.subStringIfOverLength(privacy, 200);
    }
    public void setRepository(String repository) {
        this.repository = CommonUtil.subStringIfOverLength(repository, 200);
    }
}