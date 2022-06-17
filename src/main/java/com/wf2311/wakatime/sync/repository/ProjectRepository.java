package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.ProjectEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
@ApplicationScoped
public class ProjectRepository implements PanacheRepository<ProjectEntity> {
}
