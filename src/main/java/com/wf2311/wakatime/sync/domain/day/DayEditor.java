package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.convert.EntityMappers;
import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayEditorEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <pre>
 *      {
 *           "digital": "5:39:01",
 *           "hours": 5,
 *           "minutes": 39,
 *           "name": "Chrome",
 *           "percent": 100.0,
 *           "seconds": 1,
 *           "text": "5 hrs 39 mins",
 *           "total_seconds": 20341.000000
 *         }
 * </pre>
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:01.
 */
public class DayEditor extends BaseWakatimeData {
    public DayEditorEntity convert(LocalDate day, LocalDateTime createdTime) {
        return EntityMappers.INSTANCE.convert(this, day, createdTime);

    }
}
