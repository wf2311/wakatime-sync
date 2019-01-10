package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayEditorEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * "digital": "8:09:38",
 * "hours": 8,
 * "minutes": 9,
 * "name": "IntelliJ",
 * "percent": 100,
 * "seconds": 38,
 * "text": "8 hrs 9 mins",
 * "total_seconds": 29378
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:01.
 */
public class DayEditor extends BaseWakatimeData {
    public DayEditorEntity convert(LocalDate day, LocalDateTime createdTime) {
        DayEditorEntity t = new DayEditorEntity();
        BeanUtils.copyProperties(this, t);
        t.setDay(day);
        t.setCreatedTime(createdTime);
        return t;
    }
}
