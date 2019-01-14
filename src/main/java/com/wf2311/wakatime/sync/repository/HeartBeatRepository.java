package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.HeartBeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
public interface HeartBeatRepository extends JpaRepository<HeartBeatEntity, Long> {

    long countByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    long deleteByTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    default long countByDay(LocalDate day) {
        return countByTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    default long deleteByDay(LocalDate day) {
        return deleteByTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

}
