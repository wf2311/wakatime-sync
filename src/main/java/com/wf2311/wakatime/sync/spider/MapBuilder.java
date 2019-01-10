package com.wf2311.wakatime.sync.spider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 16:10.
 */
public class MapBuilder {
    private Map<String, String> map = new HashMap<>();
    private List<String> params = new ArrayList<>();

    public static MapBuilder newBuilder() {
        return new MapBuilder();
    }


    public MapBuilder putEnd(LocalDate end) {
        return putDate("end", end);
    }

    public static MapBuilder ofDate(String key, LocalDate date) {
        return of(key, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public static MapBuilder ofDate(LocalDate date) {
        return ofDate("date", date);
    }


    public static MapBuilder of(String key, String value, String... params) {
        MapBuilder builder = new MapBuilder();
        builder.map.put(key, value);
        builder.params.addAll(Arrays.asList(params));
        return builder;
    }

    public static MapBuilder of(Map<String, String> map, String key, String value, String... params) {
        MapBuilder builder = new MapBuilder();
        if (map != null) {
            builder.map = map;
        }
        builder.map.put(key, value);
        builder.params.addAll(Arrays.asList(params));
        return builder;
    }

    public static MapBuilder of(String key, String value) {
        MapBuilder builder = new MapBuilder();
        builder.map.put(key, value);
        return builder;
    }

    public Map<String, String> build() {
        if (params != null) {
            if (params.size() % 2 != 0) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < params.size(); i += 2) {
                map.put((String) params.get(i), params.get(i + 1));
            }
        }
        return map;
    }

    public MapBuilder put(String key, String value) {
        map.put(key, value);
        return this;
    }

    public MapBuilder putDate(String key, LocalDate date) {
        put(key, date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        return this;
    }

    public MapBuilder putStart(LocalDate start) {
        return putDate("start", start);
    }
}
