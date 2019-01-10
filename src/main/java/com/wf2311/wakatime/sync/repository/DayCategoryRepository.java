package com.wf2311.wakatime.sync.repository;

import com.wf2311.wakatime.sync.entity.DayCategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:22.
 */
public interface DayCategoryRepository extends MongoRepository<DayCategoryEntity, String>, DaySummaryQueryHandler<DayCategoryEntity> {
}
