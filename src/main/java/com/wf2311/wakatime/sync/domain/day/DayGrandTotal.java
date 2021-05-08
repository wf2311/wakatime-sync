package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayGrandTotalEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 *     {
 *         "digital": "13:16",
 *         "hours": 13,
 *         "minutes": 16,
 *         "text": "13 hrs 16 mins",
 *         "total_seconds": 47771.000000
 *       }
 * </pre>
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:02.
 */
public class DayGrandTotal extends BaseWakatimeData {
    public DayGrandTotalEntity convert(LocalDate day, LocalDateTime createdTime) {
        DayGrandTotalEntity t = new DayGrandTotalEntity();
        BeanUtils.copyProperties(this, t);
        t.setDay(day);
        t.setCreatedTime(createdTime);
        return t;
    }
}
