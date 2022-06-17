package com.wf2311.wakatime.sync.spider;

import com.wf2311.wakatime.sync.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 22:03.
 */
public class JsonParser {
    public static <T> List<T> parseList(String s, Class<T> cls) {
        Map<String, Object> map = JsonUtil.toMap(s);
        String data = JsonUtil.toJson(map.get("data"));
        return JsonUtil.toList(data, cls);
    }

    public static <T> T parse(String s, Class<T> cls) {
        Map<String, Object> map = JsonUtil.toMap(s);
        String data = JsonUtil.toJson(map.get("data"));
        return JsonUtil.toObject(data, cls);
    }

    public static List<String> parseDependencies(String text) {
        if (StringUtils.isEmpty(text)) {
            return Collections.emptyList();
        }
        try {
            return JsonUtil.toList(text, String.class);
        } catch (Exception e) {
            return Collections.singletonList(text);
        }
    }
}
