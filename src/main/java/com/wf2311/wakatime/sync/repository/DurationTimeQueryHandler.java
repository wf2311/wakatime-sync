package com.wf2311.wakatime.sync.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:26.
 */
public interface DurationTimeQueryHandler<T> extends PanacheRepository<T> {


    default List<T> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return find("startTime between ?1 and ?2", startTime, endTime).list();
    }

    default long countByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return count("startTime between ?1 and ?2", startTime, endTime);
    }

    default long deleteByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return delete("startTime between ?1 and ?2", startTime, endTime);
    }

    default List<T> queryByDay(LocalDate day) {
        return findByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusSeconds(1));
    }

    default long countByDay(LocalDate day) {
        return countByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusSeconds(1));
    }

    default long deleteByDay(LocalDate day) {
        return deleteByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusSeconds(1));
    }
}
