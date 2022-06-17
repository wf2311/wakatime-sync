package com.wf2311.wakatime.sync.service;

import com.wf2311.wakatime.sync.entity.*;
import com.wf2311.wakatime.sync.repository.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:37.
 */
public abstract class AbstractDaySummaryService {
    @Inject
    protected DayCategoryRepository dayCategoryRepository;
    @Inject
    protected DayDependencyRepository dayDependencyRepository;
    @Inject
    protected DayEditorRepository dayEditorRepository;
    @Inject
    protected DayEntityRepository dayEntityRepository;
    @Inject
    protected DayGrandTotalRepository dayGrandTotalRepository;
    @Inject
    protected DayLanguageRepository dayLanguageRepository;
    @Inject
    protected DayOperateSystemRepository dayOperateSystemRepository;
    @Inject
    protected DayProjectRepository dayProjectRepository;

    protected final Map<Class<? extends BaseDayEntity>, DaySummaryQueryHandler> dayRepositoryMap = new HashMap<>(8);

    @PostConstruct
    public void init() {
        dayRepositoryMap.put(DayCategoryEntity.class, dayCategoryRepository);
        dayRepositoryMap.put(DayDependencyEntity.class, dayDependencyRepository);
        dayRepositoryMap.put(DayEditorEntity.class, dayEditorRepository);
        dayRepositoryMap.put(DayEntityEntity.class, dayEntityRepository);
        dayRepositoryMap.put(DayGrandTotalEntity.class, dayGrandTotalRepository);
        dayRepositoryMap.put(DayLanguageEntity.class, dayLanguageRepository);
        dayRepositoryMap.put(DayOperateSystemEntity.class, dayOperateSystemRepository);
        dayRepositoryMap.put(DayProjectEntity.class, dayProjectRepository);
    }
}
