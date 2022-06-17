package com.wf2311.wakatime.sync.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Json字符串转换成对象工具类
 *
 * @author ryz
 * @since 2020-05-11
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //忽略空Bean转json的错误
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    private JsonUtil() {
    }

    public static String toJson(Object o) {
        String result = null;
        if (o == null) {
            LOGGER.warn("parameter is not valid ,can not be null or empty");
            return null;
        }
        try {
            result = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Object to Json error,", e);
        }
        return result;
    }


    public static <T> T toObject(String json, Class<T> clazz) {
        T result = null;
        if (StringUtils.isBlank(json) || clazz == null) {
            LOGGER.warn("parameter is not valid ,can not be null or empty");
            return null;
        }
        try {
            result = mapper.readValue(json, clazz);
        } catch (Exception e) {
            LOGGER.warn("Json to Object error,", e);
        }
        return result;
    }

    public static <T> T toObject(String json, Class<T> clazz, Class<?>... parameterClasses) {
        T result = null;
        if (StringUtils.isBlank(json) || clazz == null) {
            LOGGER.warn("parameter is not valid ,can not be null or empty");
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(clazz, parameterClasses);
            result = mapper.readValue(json, javaType);

        } catch (Exception e) {
            LOGGER.warn("Json to Object error,", e);
        }
        return result;
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        List<T> result = null;
        if (StringUtils.isBlank(json) || clazz == null) {
            LOGGER.warn("parameter is not valid ,can not be null or empty");
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
            result = mapper.readValue(json, javaType);

        } catch (Exception e) {
            LOGGER.warn("Json to List error,", e);
        }
        return result;
    }

    public static Map<String, Object> toMap(String json) {
        return toMap(json, String.class, Object.class);
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        Map<K, V> result = null;
        if (StringUtils.isBlank(json) || kClass == null || vClass == null) {
            LOGGER.warn("parameter is not valid ,can not be null or empty");
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(LinkedHashMap.class, kClass, vClass);
            result = mapper.readValue(json, javaType);

        } catch (Exception e) {
            LOGGER.warn("Json to Map error,", e);
            e.printStackTrace();
        }
        return result;
    }

    public static <K, V> Map<K, List<V>> toMapList(String json, Class<K> kClass, Class<V> vClass) {
        Map<K, List<V>> result = null;
        if (StringUtils.isBlank(json) || kClass == null || vClass == null) {
            LOGGER.warn("parameter is not valid ,can not be null or empty");
            return null;
        }
        try {
            JavaType vType = mapper.getTypeFactory().constructParametricType(List.class, vClass);
            JavaType kType = mapper.getTypeFactory().uncheckedSimpleType(kClass);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(Map.class, kType, vType);
            result = mapper.readValue(json, javaType);

        } catch (Exception e) {
            LOGGER.warn("Json to Map error,", e);
            e.printStackTrace();
        }
        return result;
    }

}

