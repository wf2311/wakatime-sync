package com.wf2311.wakatime.sync.domain.base;

import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-09 15:55.
 */
@Data
@Builder
public class JsonResult<T> {
    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    public static <T> JsonResult<T> ok() {
        return JsonResult.<T>builder().success(true).build();
    }

    public static <T> JsonResult<T> ok(T data) {
        return JsonResult.<T>builder().success(true).data(data).build();
    }

    public static <T> JsonResult<T> ok(String message) {
        return JsonResult.<T>builder().success(true).message(message).build();
    }

    public static <T> JsonResult<T> error() {
        return JsonResult.<T>builder().success(false).build();
    }

    public static <T> JsonResult<T> error(String message) {
        return of(null, false, message, null);
    }

    public static <T> JsonResult<T> error(int code) {
        return of(code, false, null, null);
    }

    public static <T> JsonResult<T> of(Integer code, Boolean success, String message, T data) {
        return JsonResult.<T>builder().code(code).success(success).message(message).data(data).build();
    }

    public JsonResult success(boolean success) {
        this.success = success;
        return this;
    }

    public JsonResult code(int code) {
        this.code = code;
        return this;
    }

    public JsonResult message(String message) {
        this.message = message;
        return this;
    }

    public JsonResult data(T data) {
        this.data = data;
        return this;
    }
}