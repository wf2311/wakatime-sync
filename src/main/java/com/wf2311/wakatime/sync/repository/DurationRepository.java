package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.DurationEntity;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
@ApplicationScoped
public class DurationRepository implements DurationTimeQueryHandler<DurationEntity> {
}
