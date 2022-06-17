package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.convert.EntityMappers;
import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayLanguageEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 *     {
 *           "digital": "0:01:24",
 *           "hours": 0,
 *           "minutes": 1,
 *           "name": "Java",
 *           "percent": 0.18,
 *           "seconds": 24,
 *           "text": "1 min",
 *           "total_seconds": 84.619000
 *         }
 * </pre>
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:02.
 */
public class DayLanguage extends BaseWakatimeData {
    public DayLanguageEntity convert(LocalDate day, LocalDateTime createdTime) {
        return EntityMappers.INSTANCE.convert(this, day, createdTime);
    }
}
