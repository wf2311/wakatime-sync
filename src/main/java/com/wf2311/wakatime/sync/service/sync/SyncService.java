package com.wf2311.wakatime.sync.service.sync;

import com.wf2311.wakatime.sync.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 16:21.
 */
@ApplicationScoped
@Slf4j
public class SyncService {

    @Inject
    DurationService durationService;
    @Inject
    HeartBeatService heartBeatService;
    @Inject
    DaySummaryService daySummaryService;

    //    @Transactional(rollbackFor = Exception.class)
    public void sync(LocalDate start, LocalDate end) {
        LocalDate day = start;
        while (!day.isAfter(end)) {
            try {
                syncDay(day);
            } catch (Exception e) {
                CommonUtil.syncLogFail().error(e.getMessage(), e);
            }
            day = day.plusDays(1);
        }
    }

    public void syncDay(LocalDate day) {
        heartBeatService.sync(day);
        durationService.sync(day);
        daySummaryService.sync(day);
    }

    @Transactional(rollbackOn = Exception.class)
    public void syncLastDay() {
        LocalDate start = LocalDate.now().minusDays(1);
        sync(start, start);
    }

    public void sync(int day) {
        LocalDate end = LocalDate.now().minusDays(1);
        LocalDate start = LocalDate.now().minusDays(day);
        sync(start, end);
    }
}
