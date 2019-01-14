package com.wf2311.wakatime.sync.repository;


import java.time.LocalDate;
import java.util.List;


/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:26.
 */
public interface DaySummaryQueryHandler<T> {
    <S extends T> List<S> saveAll(Iterable<S> entities);

    List<T> findByDayBetween(LocalDate startDay, LocalDate endDay);

    long countByDayBetween(LocalDate startDay, LocalDate endDay);

    void deleteByDayBetween(LocalDate startDay, LocalDate endDay);

    default List<T> queryByDay(LocalDate day) {
        return findByDayBetween(day, day);
    }

    default long countByDay(LocalDate day) {
        return countByDayBetween(day, day);
    }

    default void deleteByDay(LocalDate day) {
        deleteByDayBetween(day, day);
    }

    default List<T> queryByDay(LocalDate startDay, LocalDate endDay) {
        return findByDayBetween(startDay, endDay);
    }
}
