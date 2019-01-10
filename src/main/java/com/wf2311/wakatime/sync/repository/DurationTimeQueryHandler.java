package com.wf2311.wakatime.sync.repository;

import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.wf2311.wakatime.sync.repository.QueryConsts.START_TIME_BETWEEN_QUERY;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:26.
 */
public interface DurationTimeQueryHandler<T> {

    <S extends T> List<S> saveAll(Iterable<S> entities);

    @Query(START_TIME_BETWEEN_QUERY)
    List<T> queryByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @CountQuery(START_TIME_BETWEEN_QUERY)
    long countByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @DeleteQuery(START_TIME_BETWEEN_QUERY)
    long deleteByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    default List<T> queryByDay(LocalDate day) {
        return queryByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default long countByDay(LocalDate day) {
        return countByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default long deleteByDay(LocalDate day) {
        return deleteByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default List<T> queryByDays(LocalDate startDay, LocalDate endDay) {
        return queryByStartTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }

    default long countByDays(LocalDate startDay, LocalDate endDay) {
        return countByStartTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }

    default long deleteByDays(LocalDate startDay, LocalDate endDay) {
        return deleteByStartTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }
}
