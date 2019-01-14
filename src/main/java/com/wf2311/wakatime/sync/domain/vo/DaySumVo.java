package com.wf2311.wakatime.sync.domain.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 23:45.
 */
@Data
public class DaySumVo {
    private LocalDate date;
    private Integer total;
}
