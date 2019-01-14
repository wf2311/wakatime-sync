package com.wf2311.wakatime.sync.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 14:40.
 */
@Slf4j
public class CommonUtil {
    private CommonUtil() {
    }

    private static final String SUB_PRE = "...";

    public static long getTimestamp(LocalDateTime time) {
        return Timestamp.valueOf(time).getTime();
    }

    public static Logger syncLog() {
        return LoggerFactory.getLogger("syncLog");
    }

    public static Logger syncLogFail() {
        return LoggerFactory.getLogger("syncLogFail");
    }

    public static String subStringIfOverLength(String s, int max) {
        if (s == null || s.length() <= max) {
            return s;
        }
        String sub = SUB_PRE + s.substring(s.length() - max + 4);
        log.info("string:[" + s + "] length over " + max+"\n sub to ["+sub+"]");

        return sub;
    }
}
