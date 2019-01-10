package com.wf2311.wakatime.sync.repository;


import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.wf2311.wakatime.sync.repository.QueryConsts.DAY_BETWEEN_QUERY;


/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:26.
 */
public interface DaySummaryQueryHandler<T> {

    <S extends T> List<S> saveAll(Iterable<S> entities);

    @Query(DAY_BETWEEN_QUERY)
    List<T> queryByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @CountQuery(DAY_BETWEEN_QUERY)
    long countByDayBetween(LocalDateTime startTime, LocalDateTime endTime);

    @DeleteQuery(DAY_BETWEEN_QUERY)
    void deleteByDayBetween(LocalDateTime startTime, LocalDateTime endTime);

    default List<T> queryByDay(LocalDate day) {
        return queryByTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default long countByDay(LocalDate day) {
        return countByDayBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default void deleteByDay(LocalDate day) {
        deleteByDayBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default List<T> queryByDay(LocalDate startDay, LocalDate endDay) {
        return queryByTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }

    default long countByDay(LocalDate startDay, LocalDate endDay) {
        return countByDayBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }

    default long deleteByDay(LocalDate startDay, LocalDate endDay) {
        return countByDayBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }

}
