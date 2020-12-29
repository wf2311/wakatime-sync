package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
public interface TimeRepository extends JpaRepository<Time, Long> {
    List<Time> findAllByDay(LocalDate day);
}
