package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.ProjectDurationEntity;
import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.wf2311.wakatime.sync.repository.QueryConsts.START_TIME_BETWEEN_AND_PROJECT_QUERY;


/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
public interface ProjectDurationRepository extends MongoRepository<ProjectDurationEntity, String>, DurationTimeQueryHandler<ProjectDurationEntity> {
    @Query(START_TIME_BETWEEN_AND_PROJECT_QUERY)
    List<ProjectDurationEntity> queryByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime, String project);

    @CountQuery(START_TIME_BETWEEN_AND_PROJECT_QUERY)
    long countByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime, String project);

    @DeleteQuery(START_TIME_BETWEEN_AND_PROJECT_QUERY)
    long deleteByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime, String project);


    default List<ProjectDurationEntity> queryByDay(LocalDate day, String project) {
        return queryByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1), project);
    }

    default long countByDay(LocalDate day, String project) {
        return countByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1), project);
    }

    default long deleteByDay(LocalDate day, String project) {
        return deleteByStartTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1), project);
    }

    default List<ProjectDurationEntity> queryByDays(LocalDate startDay, LocalDate endDay, String project) {
        return queryByStartTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1), project);
    }

    default long countByDays(LocalDate startDay, LocalDate endDay, String project) {
        return countByStartTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1), project);
    }

    default long deleteByDays(LocalDate startDay, LocalDate endDay, String project) {
        return deleteByStartTimeBetween(startDay.atStartOfDay(), endDay.atStartOfDay().plusDays(1).minusNanos(1), project);
    }
}
