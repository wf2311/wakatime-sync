package com.wf2311.wakatime.sync.controller.api;

import com.wf2311.wakatime.sync.config.WakatimeProperties;
import com.wf2311.wakatime.sync.domain.base.JsonResult;
import com.wf2311.wakatime.sync.service.sync.SyncService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * @author <a href="mailto:wf2311@163.com">wf2311</a>
 * @since 2019-01-10 16:54.
 */
@Path("/api/v1")
public class SyncApi {
    @Inject
    SyncService syncService;
    @Inject
    private WakatimeProperties wakatimeProperties;

    @GET
    @Path("/sync")
    @Operation(summary = "同步数据", description = "同步数据")
    @Parameters(
            value = {
                    @Parameter(name = "day", description = "天数", required = true),
                    @Parameter(name = "apiKey", description = "密码", required = true),
            }
    )
    public JsonResult sync(@QueryParam("day") int day, @QueryParam("apiKey") String apiKey) {
        if (StringUtils.isEmpty(apiKey) || !apiKey.equals(wakatimeProperties.getSecretApiKey())) {
            throw new IllegalArgumentException();
        }
        syncService.sync(day);
        return JsonResult.ok();
    }
}
