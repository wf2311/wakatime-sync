package com.wf2311.wakatime.sync.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-12 17:04.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayTypeGroupSummaryUnit implements Serializable {
    private String id;
    private Integer seconds;
}
