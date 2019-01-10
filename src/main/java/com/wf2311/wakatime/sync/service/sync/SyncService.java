package com.wf2311.wakatime.sync.service.sync;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 16:21.
 */
@Service
public class SyncService {

    @Resource
    private DurationService durationService;
    @Resource
    private HeartBeatService heartBeatService;
    @Resource
    private DaySummaryService daySummaryService;
    @Transactional(rollbackFor = Exception.class)
    public void sync(LocalDate start, LocalDate end) {
        LocalDate day = start;
        while (!day.isAfter(end)) {
            heartBeatService.sync(day);
            durationService.sync(day);
            daySummaryService.sync(day);
            day = day.plusDays(1);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void sync() {
        LocalDate start = LocalDate.now().minusDays(1);
        sync(start, start);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sync(int day) {
        LocalDate end = LocalDate.now().minusDays(1);
        LocalDate start = LocalDate.now().minusDays(day);
        sync(start, end);
    }
}
