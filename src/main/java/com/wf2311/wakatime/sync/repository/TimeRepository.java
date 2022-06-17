package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.Time;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
@ApplicationScoped
public class TimeRepository implements PanacheRepository<Time> {
    public List<Time> findAllByDay(LocalDate day) {
        return find("day = ?1", day).list();
    }
}
