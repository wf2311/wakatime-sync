package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.convert.EntityMappers;
import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayEntityEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * "digital": "0:35:13",
 * "hours": 0,
 * "minutes": 35,
 * "name": "D:\\xx\\xxx\\ReportService.java",
 * "percent": 7.2,
 * "seconds": 13,
 * "text": "35 mins",
 * "total_seconds": 2113,
 * "type": "file"
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:02.
 */
@Deprecated
public class DayEntity extends BaseWakatimeData {
    public DayEntityEntity convert(LocalDate day, LocalDateTime createdTime) {
        return EntityMappers.INSTANCE.convert(this, day, createdTime);
    }
}
