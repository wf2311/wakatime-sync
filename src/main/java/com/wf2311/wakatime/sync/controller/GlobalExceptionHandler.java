package com.wf2311.wakatime.sync.controller;

import com.wf2311.wakatime.sync.domain.base.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-20 13:44.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult illegalArgumentHandler(HttpServletRequest request, IllegalArgumentException e) {
        String error = "参数错误";
        if (StringUtils.isNotEmpty(e.getMessage())) {
            error += ":" + e.getMessage();
        }
        return JsonResult.error(error);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult defaultHandler(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(),e);
        return JsonResult.error("服务器异常");
    }
}
