package com.wf2311.wakatime.sync.message;

import com.alibaba.fastjson.JSON;
import com.wf2311.wakatime.sync.domain.vo.SimpleDayDurationVo;
import com.wf2311.wakatime.sync.util.CommonUtil;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-17 22:59.
 */
@Service
@Slf4j
public class DingDingMessage extends AbstractMessage {
    private static final String DINGDING_WEB_HOOK_URL = "https://oapi.dingtalk.com/robot/send?access_token=%s";

    @Override
    public void sendMessage(SimpleDayDurationVo info) {
        if (!CommonUtil.hasValue(wakatimeProperties.getDingdingKey())) {
            log.info("未配置钉钉通知机器人，无效发送钉钉同步通知");
            return;
        }
        String title = getMessageTitle(info.getDay());
        String content = formatMarkdownMessage(info);
        try {
            HttpResponse resp = HttpRequest
                    .post(String.format(DINGDING_WEB_HOOK_URL, wakatimeProperties.getDingdingKey()))
                    .bodyText(formatMessageContent(title, content), "application/json; charset=utf-8")
                    .send();
            if (log.isDebugEnabled()) {
                log.debug("response:{}", resp.bodyText());
            }
        } catch (Exception e) {
            log.error(String.format("发送【%s】钉钉消息失败，错误原因：\n%s", title, e.getMessage()), e);
        }
    }


    private static String formatMessageContent(String title, String content) {
        HashMap<String, Object> l1 = new HashMap<>();
        HashMap<String, String> l2 = new HashMap<>();
        l2.put("title", title);
        l2.put("text", "#### " + title + "\n" + content);
        l1.put("msgtype", "markdown");
        l1.put("markdown", l2);
        return JSON.toJSONString(l1);
    }
}
