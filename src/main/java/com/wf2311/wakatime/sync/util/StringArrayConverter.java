package com.wf2311.wakatime.sync.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-13 15:59.
 */
public class StringArrayConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        return JSON.toJSONString(list);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (StringUtils.isEmpty(s)) {
            return Collections.emptyList();
        }
        return JSON.parseArray(s, String.class);
    }
}
