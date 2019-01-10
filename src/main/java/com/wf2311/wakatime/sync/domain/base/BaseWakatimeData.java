package com.wf2311.wakatime.sync.domain.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 15:57.
 */
@Data
public class BaseWakatimeData {
    protected String digital;
    protected Integer hours = 0;
    protected Integer minutes = 0;
    protected Integer seconds = 0;
    @JSONField(name = "total_seconds")
    protected Integer totalSeconds = 0;
    protected String text;
    protected String day;
    protected Double percent;
    protected String name;
}
