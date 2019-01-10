package com.wf2311.wakatime.sync.domain.day;

import com.wf2311.wakatime.sync.domain.base.BaseWakatimeData;
import com.wf2311.wakatime.sync.entity.DayDependencyEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * digital: "0:35:54",
 * hours: 0,
 * minutes: 35,
 * name: "varena.tournament",
 * percent: 10.64,
 * seconds: 54,
 * text: "35 mins",
 * total_seconds: 2154
 *
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:00.
 */
public class DayDependency extends BaseWakatimeData {
    public DayDependencyEntity convert(LocalDate day, LocalDateTime createdTime) {
        DayDependencyEntity t = new DayDependencyEntity();
        BeanUtils.copyProperties(this, t);
        t.setDay(day);
        t.setCreatedTime(createdTime);
        return t;
    }
}
