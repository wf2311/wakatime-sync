package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.HeartBeatEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
@ApplicationScoped
public class HeartBeatRepository implements PanacheRepository<HeartBeatEntity> {

    public long countByTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return count("time between ?1 and ?2", startTime, endTime);
    }

    public long deleteByTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return delete("time between ?1 and ?2", startTime, endTime);
    }

    public long countByDay(LocalDate day) {
        return countByTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

    public long deleteByDay(LocalDate day) {
        return deleteByTimeBetween(day.atStartOfDay(), day.plusDays(1).atStartOfDay().minusNanos(1));
    }

}
