package com.wf2311.wakatime.sync.message;

import com.wf2311.wakatime.sync.domain.vo.SimpleDayDurationVo;
import com.wf2311.wakatime.sync.service.query.QueryWakatimeDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-17 23:19.
 */
@ApplicationScoped
public class MessageFactory {
    @Inject
    private QueryWakatimeDataService queryWakatimeDataService;
    @Inject
    private FtqqMessage ftqqMessage;
    @Inject
    private DingDingMessage dingDingMessage;

    public void sendDayWakatimeInfo(LocalDate day) {
        SimpleDayDurationVo info = queryWakatimeDataService.findSimpleDayDurationInfo(day);
        ftqqMessage.sendMessage(info);
        dingDingMessage.sendMessage(info);
    }
}
