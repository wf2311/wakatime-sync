package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayCategoryEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * digital: "9:02:09",
 * hours: 9,
 * minutes: 2,
 * name: "Browsing",
 * percent: 75.95,
 * seconds: 9,
 * text: "9 hrs 2 mins",
 * total_seconds: 32529
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
