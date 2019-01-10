package com.wf2311.wakatime.sync.convert;

import jodd.util.StringUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 15:13.
 */
public class EntityConvertHelper {
    public static String getSimpleName(String name) {
        if (StringUtil.isEmpty(name)) {
            return name;
        }
        int subStart = 0;
        if (name.contains("/")) {
            subStart = name.lastIndexOf("/") + 1;
        }
        if (name.contains("\\")) {
            subStart = name.lastIndexOf("\\") + 1;
        }
        return name.substring(subStart);
    }

    public static LocalDateTime doubleToDate(Double d) {
        return new Timestamp(Math.round(d * 1000)).toLocalDateTime();
    }
}
