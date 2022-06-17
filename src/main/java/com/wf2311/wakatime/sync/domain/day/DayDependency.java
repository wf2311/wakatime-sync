package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.convert.EntityMappers;
import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayDependencyEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 *  {
 *       "digital": "3:28:33",
 *       "hours": 3,
 *       "minutes": 28,
 *       "name": "springframework.beans",
 *       "percent": 10.93,
 *       "seconds": 33,
 *       "text": "3 hrs 28 mins",
 *       "total_seconds": 12513.7979977130889892577775
 *     }
 * </pre>
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:00.
 */
public class DayDependency extends BaseWakatimeData {
    public DayDependencyEntity convert(LocalDate day, LocalDateTime createdTime) {
        return EntityMappers.INSTANCE.convert(this, day, createdTime);
    }
}
