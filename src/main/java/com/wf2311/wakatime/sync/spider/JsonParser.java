package com.wf2311.wakatime.sync.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 22:03.
 */
public class JsonParser {
    public static <T> List<T> parseList(String s, Class<T> cls) {
        JSONObject object = JSONObject.parseObject(s);
        JSONArray data = object.getJSONArray("data");
        return data.toJavaList(cls);
    }

    public static <T> T parse(String s, Class<T> cls) {
        JSONObject object = JSONObject.parseObject(s);
        return JSON.parseObject(object.getString("data"), cls);
    }

    public static List<String> parseDependencies(String text) {
        if (StringUtils.isEmpty(text)) {
            return Collections.emptyList();
        }
        try {
            return JSONArray.parseArray(text, String.class);
        } catch (Exception e) {
            return Collections.singletonList(text);
        }
    }
}
