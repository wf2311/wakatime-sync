package com.wf2311.wakatime.sync.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-13 00:33.
 */
@Data
public class DayDurationVo {
    private String project;
    private LocalDateTime startTime;
    private Double duration;
}
