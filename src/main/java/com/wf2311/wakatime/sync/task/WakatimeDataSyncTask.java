package com.wf2311.wakatime.sync.task;

import com.wf2311.wakatime.sync.message.MessageFactory;
import com.wf2311.wakatime.sync.service.sync.SyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-11 13:52.
 */
@Component
@Slf4j
public class WakatimeDataSyncTask {

    @Resource
    private SyncService syncService;
    @Resource
    private MessageFactory messageFactory;

    /**
     * 每天凌晨00：05同步上一天的数据
     */
    @Scheduled(cron = "0 05 0 * * ?")
    public void syncYesterday() {
        syncService.syncLastDay();
    }

    /**
     * 每天早上09:00发送上一天的编程数据记录消息
     */
    @Scheduled(cron = "0 00 9 * * ?")
    public void sendYesterdayDataInfo() {
        messageFactory.sendDayWakatimeInfo(LocalDate.now().minusDays(1));
    }
}
