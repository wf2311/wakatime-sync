package com.wf2311.wakatime.sync.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:40.
 */
public class CommonUtil {
    private CommonUtil() {
    }

    public static long getTimestamp(LocalDateTime time) {
        return Timestamp.valueOf(time).getTime();
    }

    public static Logger syncLog() {
        return LoggerFactory.getLogger("syncLog");
    }
}
