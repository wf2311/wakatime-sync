package com.wf2311.wakatime.sync.message;

import com.wf2311.wakatime.sync.domain.vo.SimpleDayDurationVo;
import com.wf2311.wakatime.sync.service.query.QueryWakatimeDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-17 23:19.
 */
@Component
public class MessageFactory {
    @Resource
    private QueryWakatimeDataService queryWakatimeDataService;
    @Resource
    private FtqqMessage ftqqMessage;
    @Resource
    private DingDingMessage dingDingMessage;

    public void sendDayWakatimeInfo(LocalDate day) {
        SimpleDayDurationVo info = queryWakatimeDataService.findSimpleDayDurationInfo(day);
        ftqqMessage.sendMessage(info);
        dingDingMessage.sendMessage(info);
    }
}
