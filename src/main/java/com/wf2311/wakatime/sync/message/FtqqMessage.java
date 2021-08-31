package com.wf2311.wakatime.sync.message;

import com.wf2311.wakatime.sync.domain.vo.SimpleDayDurationVo;
import com.wf2311.wakatime.sync.util.CommonUtil;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-17 22:49.
 */
@Component
@Slf4j
public class FtqqMessage extends AbstractMessage {
    private static final String FTQQ_URL = "https://sctapi.ftqq.com/%s.send";


    @Override
    public void sendMessage(SimpleDayDurationVo info) {
        if (!CommonUtil.hasValue(wakatimeProperties.getFtqqKey())) {
            log.info("未配置ftqq通知机器人，无效发送ftqq同步通知");
            return;
        }
        String title = getMessageTitle(info.getDay());
        String content = formatMarkdownMessage(info);
        try {
            HttpResponse resp = HttpRequest
                    .post(String.format(FTQQ_URL, wakatimeProperties.getFtqqKey()))
                    .contentType("application/x-www-form-urlencoded")
                    .form("title", title)
                    .form("desp", content)
                    .send();
            if (log.isDebugEnabled()) {
                log.debug("response:{}", resp.bodyText());
            }
        } catch (Exception e) {
            log.error(String.format("发送【%s】ftqq消息失败，错误原因：\n%s", title, e.getMessage()), e);
        }
    }
}
