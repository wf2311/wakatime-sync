package com.wf2311.wakatime.sync.entity;

import com.wf2311.wakatime.sync.util.CommonUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:15.
 */
@Data
@Entity
@Table(name = "day_editor")
public class DayEditorEntity extends PanacheEntityBase implements BaseDayEntity {
    /**
     * 主键
     */
    @Id
    @Column(name = "id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name" , length = 200)
    private String name;

    /**
     * 总时间(秒)
     */
    private Integer totalSeconds;

    /**
     * 时间
     */
    private LocalDate day;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    public void setName(String name) {
        this.name = CommonUtil.subStringIfOverLength(name, 200);
    }
}