package com.wf2311.wakatime.sync.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:15.
 */
@Data
@Document(collection = "day_language")
public class DayLanguageEntity implements BaseDayEntity {
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
     * 小时
     */
    private Integer hours;

    /**
     * 分钟
     */
    private Integer minutes;

    /**
     * 秒
     */
    private Integer seconds;

    /**
     * 总时间(秒)
     */
    private Integer totalSeconds;

    /**
     * 所占百分比
     */
    private Double percent;

    /**
     * 时间
     */
    private LocalDate day;


    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}