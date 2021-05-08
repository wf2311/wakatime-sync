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
@Table(name = "heart_beat")
public class HeartBeatEntity {
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
     * 简单名称
     */
    private String simpleName;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 类型
     */
    private String type;


    /**
     * 创建时间
     */
    private LocalDateTime createdTime;


    public void setName(String name) {
        this.name = CommonUtil.subStringIfOverLength(name, 255);
    }


    public void setSimpleName(String simpleName) {
        this.simpleName = CommonUtil.subStringIfOverLength(simpleName, 100);
    }


    public void setType(String type) {
        this.type = CommonUtil.subStringIfOverLength(type, 20);
    }
}