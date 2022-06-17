package com.wf2311.wakatime.sync.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:26.
 */
public interface DaySummaryQueryHandler<T> extends PanacheRepository<T> {

    default List<T> findByDayBetween(LocalDate startDay, LocalDate endDay) {
        return find("day between ?1 and ?2", startDay, endDay).list();
    }

    default long countByDayBetween(LocalDate startDay, LocalDate endDay) {
        return count("day between ?1 and ?2", startDay, endDay);
    }

    @Transactional
    default void deleteByDayBetween(LocalDate startDay, LocalDate endDay) {
        delete("day between ?1 and ?2", startDay, endDay);
    }

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
