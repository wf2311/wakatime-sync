package com.wf2311.wakatime.sync.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:26.
 */
public interface DurationTimeQueryHandler<T> {

    <S extends T> List<S> saveAll(Iterable<S> entities);

    List<T> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    long countByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    long deleteByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

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
