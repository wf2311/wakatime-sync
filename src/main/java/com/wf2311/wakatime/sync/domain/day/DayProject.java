package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.convert.EntityMappers;
import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayProjectEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:03.
 */
public class DayProject extends BaseWakatimeData {
    public DayProjectEntity convert(LocalDate day, LocalDateTime createdTime) {
        return EntityMappers.INSTANCE.convert(this, day, createdTime);
    }
}
