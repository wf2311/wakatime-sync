package com.wf2311.wakatime.sync.convert;

import jodd.util.StringUtil;

import javax.persistence.Query;
import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public static <T> T map(Class<T> type, Object[] tuple) {
        List<Class<?>> tupleTypes = new ArrayList<>();
        for (Object field : tuple) {
            tupleTypes.add(field.getClass());
        }
        try {
            Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tuple.length]));
            return ctor.newInstance(tuple);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> map(Class<T> type, List<Object[]> records) {
        List<T> result = new LinkedList<>();
        for (Object[] record : records) {
            result.add(map(type, record));
        }
        return result;
    }

    public static <T> List<T> getResultList(Query query, Class<T> type) {
        @SuppressWarnings("unchecked")
        List<Object[]> records = query.getResultList();
        return map(type, records);
    }
}
