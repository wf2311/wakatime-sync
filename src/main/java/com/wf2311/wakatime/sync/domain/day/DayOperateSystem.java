package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.convert.EntityMappers;
import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayOperateSystemEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 *     {
 *           "digital": "13:16:11",
 *           "hours": 13,
 *           "minutes": 16,
 *           "name": "Mac",
 *           "percent": 100.0,
 *           "seconds": 11,
 *           "text": "13 hrs 16 mins",
 *           "total_seconds": 47771.000000
 *         }
 * </pre>
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:03.
 */
public class DayOperateSystem extends BaseWakatimeData {
    public DayOperateSystemEntity convert(LocalDate day, LocalDateTime createdTime) {
        return EntityMappers.INSTANCE.convert(this, day, createdTime);
    }
}
