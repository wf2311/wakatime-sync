package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.HeartBeatEntity;
import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
public interface HeartBeatRepository extends MongoRepository<HeartBeatEntity, String> {
    String TIME_BETWEEN_QUERY = "{'time':{ $gte: ?0, $lte: ?1}}";

    @Query(TIME_BETWEEN_QUERY)
    List<HeartBeatEntity> queryByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @CountQuery(TIME_BETWEEN_QUERY)
    long countByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @DeleteQuery(TIME_BETWEEN_QUERY)
    long deleteByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    default List<HeartBeatEntity> queryByDay(LocalDate day) {
        return queryByTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default long countByDay(LocalDate day) {
        return countByTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default long deleteByDay(LocalDate day) {
        return deleteByTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default List<HeartBeatEntity> queryByDays(LocalDate startDay, LocalDate endDay) {
        return queryByTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }

    default long countByDays(LocalDate startDay, LocalDate endDay) {
        return countByTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }

    default long deleteByDays(LocalDate startDay, LocalDate endDay) {
        return deleteByTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1));
    }
}
