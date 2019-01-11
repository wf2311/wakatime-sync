package com.wf2311.wakatime.sync.controller.api;

import com.wf2311.wakatime.sync.config.WakatimeProperties;
import com.wf2311.wakatime.sync.domain.base.JsonResult;
import com.wf2311.wakatime.sync.service.sync.SyncService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 16:54.
 */
@RestController
@RequestMapping("/api/v1")
public class SyncApi {
    @Resource
    private SyncService syncService;

    @PostMapping("/sync")
    public JsonResult sync(int day, String apiKey) {
        if (StringUtils.isEmpty(apiKey) || !apiKey.equals(WakatimeProperties.SECRET_API_KEY)) {
            throw new IllegalArgumentException();
        }
        syncService.sync(day);
        return JsonResult.ok();
    }
}
