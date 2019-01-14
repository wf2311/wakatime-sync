package com.wf2311.wakatime.sync.entity;

import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:14.
 */
public interface BaseDayEntity {
    Long getId();

    String getName();

    Integer getTotalSeconds();

    LocalDate getDay();
}
