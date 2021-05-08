package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayCategoryEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * example:
 * <pre>
 *     {
 *       "digital": "6:13:59",
 *       "hours": 6,
 *       "minutes": 13,
 *       "name": "Browsing",
 *       "percent": 51.39,
 *       "seconds": 59,
 *       "text": "6 hrs 13 mins",
 *       "total_seconds": 22439.4080009460449218750125
 *     }
 * </pre>
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 15:59.
 */
public class DayCategory extends BaseWakatimeData {
    public DayCategoryEntity convert(LocalDate day, LocalDateTime createdTime) {
        DayCategoryEntity t = new DayCategoryEntity();
        BeanUtils.copyProperties(this, t);
        t.setDay(day);
        t.setCreatedTime(createdTime);
        return t;
    }
}
